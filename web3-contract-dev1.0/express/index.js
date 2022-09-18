const express = require("express")
const cors = require('cors')
const app = express()
const port = 8080

app.use(cors())
app.use(express.json())


app.get("/", (req, res) => {
    res.send("Hello")
})

app.get("/address/0xf39fd6e51aad88f6f4ce6ab8827279cfffb92266", (req, res) => {
    res.send("Hello1")
})

app.post("/", (req, res) => {
    console.log(req.body.blogName)
    res.status(201).send({ obj: [1, 2, 3] })
})

app.put("/:id", (req, res) => {
    console.log("收到请求体,id 位:", req.params.id)
    console.log("收到请求体:", req.body)

    res.send()
})


app.delete("/:id", (req, res) => {
    console.log("收到请求参数,id 为:", req.params.id)
    res.status(204).send()
})
app.listen(port, () => {
    console.log(`Server listening at port:${port}`)
})