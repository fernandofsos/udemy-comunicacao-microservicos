import expreess from "express";

const app = expreess();
const env = process.env;
const PORT = env.PORT || 8082;

app.get('/api/status', (req, resp)=> {
    return resp.status(200).json({
        service: "Sales-api",
        astatus: "up",
        httpStatus: 200
    });
})

app.listen(PORT, () => {
    console.info(`Server started successfully at port ${PORT}`);
})