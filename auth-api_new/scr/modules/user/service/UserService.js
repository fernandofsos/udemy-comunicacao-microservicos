import bcrypt from "bcrypt";
import jwt from "jsonwebtoken";

import UserRepository from "../repository/UserRepository.js";
import UserException from "../exception/UserException.js";
import * as httpStatus from "../../../config/constants/httpStatus.js";
import * as secrets from "../../../config/constants/secrets.js";

class UserService {
  async findByEmail(req) {
    try {
      console.log("----------------01------------UserService findByEmail");
      const { email } = req.params;
      const { authUser } = req;
      console.log(email);
      console.log(authUser);
      console.log("----------------02------------");
      this.validateRequestData(email);
      console.log("----------------03------------");
      let user = await UserRepository.findByEmail(email);
      console.log("----------------04------------");
      this.validateUserNotFound(user);
      console.log("----------------05------------");
      this.validateAuthenticatedUser(user, authUser);
      console.log("----------------06------------");
      return {
        status: httpStatus.SUCCESS,
        user: {
          id: user.id,
          name: user.name,
          email: user.email,
        },
      };
    } catch (err) {
      console.log("----------------07------------");
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }

  async getAccessToken(req) {
    try {
      console.log("---------01-------------------------------");
      const { transactionid, serviceid } = req.headers;
      console.info(
        `Request to POST login with data ${JSON.stringify(
          req.body
        )} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`
      );
      const { email, password } = req.body;
      console.log("---------02-----------------------------------");
      this.validateAccessTokenData(email, password);
      console.log("---------03-----------------------------------");
      let user = await UserRepository.findByEmail(email);
      console.log("---------04-----------------------------------");
      this.validateUserNotFound(user);
      console.log("---------05-----------------------------------");
      await this.validatePassword(password, user.password);
      console.log("---------06-----------------------------------");
      const authUser = { id: user.id, name: user.name, email: user.email };
      console.log("---------07-----------------------------------");
      const accessToken = jwt.sign({ authUser }, secrets.API_SECRET, {
        expiresIn: "1d",
      });
      console.log("---------08-----------------------------------");
      let response = {
        status: httpStatus.SUCCESS,
        accessToken,
      };
      console.info(
        `Response to POST login with data ${JSON.stringify(
          response
        )} | [transactionID: ${transactionid} | serviceID: ${serviceid}]`
      );
      console.log("---------09-----------------------------------");
      return response;
    } catch (err) {
      console.log("---------10-----------------------------------");
      return {
        status: err.status ? err.status : httpStatus.INTERNAL_SERVER_ERROR,
        message: err.message,
      };
    }
  }
  
  validateRequestData(email) {
    console.log("--------validateRequestData");
    if (!email) {
      throw new UserException("User email was not informed.");
    }
  }

  validateUserNotFound(user) {
    if (!user) {
      throw new UserException(httpStatus.BAD_REQUEST, "User was not found.");
    }
  }

  validateAuthenticatedUser(user, authUser) {
    if (!authUser || user.id !== authUser.id) {
      throw new UserException(
        httpStatus.FORBIDDEN,
        "You cannot see this user data."
      );
    }
  }

  validateAccessTokenData(email, password) {
    console.log("********validateAccessTokenData");
    console.log(email);
    console.log(password);
    if (!email || !password) {
      throw new UserException(
        httpStatus.UNAUTHORIZED,
        "Email and password must be informed."
      );
    }
  }

  async validatePassword(password, hashPassword) {
    console.log("*********valida password");
    console.log(password)
    console.log(hashPassword)
    if (!(await bcrypt.compare(password, hashPassword))) {
      throw new UserException(
        httpStatus.UNAUTHORIZED,
        "Password doesn't match."
      );
    }
  }
}

export default new UserService();
