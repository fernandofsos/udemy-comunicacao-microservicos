import jwt from "jsonwebtoken";
import { promisify } from 'util';

import AuthException from "./authException.js";
import * as secrets from "../constants/secrets.js";
import * as httpStatus from "../constants/httpStatus.js";

const bearer = "bearer ";
const emptySpace = " ";

export default async (req, res, next) => {
    try {
        const { authorization } = req.headers;
        if(!authorization){
           throw new AuthException(httpStatus.UNAUTHORIZED, "Access Token was not informed.");
        }
        let accessToken = authorization;
        if(accessToken.includes(emptySpace)){
            accessToken = accessToken.split(emptySpace)[1];
        }else{
            accessToken = authorization;
        }
        //verifica se a chave do accesstoken e igual a secrets
        const decoded = await promisify(jwt.verify)(accessToken,secrets.API_SECRET);
        req.authUser = decoded.authUser;
        return next();
    } catch (error) {
        return res.status(error.status).json({
            status: error.status? error.status : httpStatus.INTERNAL_SERVER_ERROR,
            message: error.message
        });
    }
}


