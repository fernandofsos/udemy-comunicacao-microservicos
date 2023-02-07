import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

import UserRepository from "../repository/UserRepository.js";
import * as httpStatus from "../../../config/constants/httpStatus.js"
import * as secrets from "../../../config/constants/secrets.js";

import UserException from "../exception/UserException.js";

class UserService{

    async getAccessToken(req){

        try {
            const { email, password } = req.body;
            console.log(email);
            console.log(password);
            this.validateAccessTokenData(email, password);
            let user = await UserRepository.findByEmail(email);
            
            this.validateUserNotFound(user);
            await this.validatePassword(password, user.password);
            const authUser = { id: user.id , name: user.name , email: user.email}
            const accessToken = jwt.sign({authUser}, secrets.API_SECRET,{ expiresIn: '1d' })
            return {
                status: httpStatus.SUCCESS,
                accessToken,
            }
        } catch (error) {
            return {
                status: error.status? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.message
            }
        }
    }

    async findByEmail(req){
        try {
          const { email } = req.params;
          const { authUser } = req;
          this.validarDadosRequisicao(email);
          let user = await UserRepository.findByEmail(email);   
          this.validateUserNotFound(user);
          this.validateAuthenticateUser(authUser);
          return {
            status: httpStatus.SUCCESS,
            user:{
                id: user.id,
                nome: user.nome,
                email: user.email
            }
          };
        } catch (error) {
            return {
                status: error.status? error.status : httpStatus.INTERNAL_SERVER_ERROR,
                message: error.message
            }
        }

    }


    validarDadosRequisicao(email){
        if(!email){
            throw new UserException(httpStatus.BAD_REQUEST ,'User email was not informed.')
        }
    }


    validateUserNotFound(user){
        console.log("*************entrou validateUserNotFound**************");
        if(!user){
            throw new UserException(httpStatus.BAD_REQUEST ,'User was not found.')
        }
        console.log("*************saiu validateUserNotFound**************");
    }

    validateAccessTokenData(email, password){
        console.log("--------entrou no validateAccessTokenData------- ");
        if(!email || !password){
            throw new UserException(httpStatus.UNAUTHORIZED, "email and password must be informed.")
        }
        console.log("--------saiu no validateAccessTokenData------- ");
    }

    async validatePassword(password, hashPassword){
        if (!await bcrypt.compare(password,hashPassword)) {
            throw new UserException(httpStatus.UNAUTHORIZED, "Pssword dosen't match.")
        }
    }


    validateAuthenticateUser(user, authUser){
        if(!authUser || user.id !== authUser.id){
            throw new UserException(httpStatus.FORBIDDEN,"you cannot see this user data." );
        }
    }

}
export default new UserService();
