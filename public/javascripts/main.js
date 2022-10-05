const read = () => document.getElementById("message").value

const print = s => {
    const lg = document.getElementById("log")
    lg.value += s
}

const println = s => print(`${s}\n`)

let socket

const open = () => {
    socket = new WebSocket("ws://localhost:9000/ws")

    socket.onopen = function (e) {
        println("-- connection has been established")
    }

    socket.onmessage = function (event) {
        println(`<< ${event.data}`)
    }

    socket.onclose = function (event) {
        if (event.wasClean) {
            println(`-- connection closed`)
        } else {
            println('-- connection is died')
        }
    }

    socket.onerror = function (error) {
        println(`-- error: ${error.message}`)
    }
}

const close = () => {
    socket.close()
}


// opening socket connection
//open()

// binding socket commands
const sockOpen = document.getElementById("open")
sockOpen.onclick = e => open()
const sockClose = document.getElementById("close")
sockClose.onclick = e => close()

// binding keydown event
const input = document.getElementById("message")
input.onkeydown = event => {
    if (event.key === "Enter") {
        const line = read()
        input.value = ""
        socket.send(line)
    }
}
