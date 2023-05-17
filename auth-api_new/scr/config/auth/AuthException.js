class AuthException extends Error {
  constructor(status, message) {
    console.log("Passou aqui AuthException");
    super(message);
    this.status = status;
    this.message = message;
    this.name = this.constructor.name;
    Error.captureStackTrace(this, this.constructor);
  }
}

export default AuthException;
