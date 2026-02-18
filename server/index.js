const express = require("express")
const cors = require("cors")
require("dotenv").config()

const { PrismaClient } = require("@prisma/client")

// IMPORTANT: no arguments inside PrismaClient()
const prisma = new PrismaClient()

const app = express()

app.use(cors())
app.use(express.json())

// Health check route
app.get("/api/health", (req, res) => {
  res.status(200).json({ message: "Server is running" })
})

// GET all trips
app.get("/api/trips", async (req, res) => {
  try {
    const trips = await prisma.trip.findMany({
      orderBy: { createdAt: "desc" }
    })

    console.log("GET /api/trips")
    res.status(200).json(trips)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Server error" })
  }
})

// CREATE trip
app.post("/api/trips", async (req, res) => {
  try {
    const { title, destination, startDate, endDate, notes } = req.body

    if (!title || title.trim() === "") {
      return res.status(400).json({ message: "Title is required" })
    }

    if (!destination || destination.trim() === "") {
      return res.status(400).json({ message: "Destination is required" })
    }

    const trip = await prisma.trip.create({
      data: {
        title: title.trim(),
        destination: destination.trim(),
        startDate: startDate || null,
        endDate: endDate || null,
        notes: notes || null
      }
    })

    console.log("POST /api/trips", trip.id)
    res.status(201).json(trip)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Server error" })
  }
})

// UPDATE trip
app.put("/api/trips/:id", async (req, res) => {
  try {
    const id = Number(req.params.id)
    const { title, destination, startDate, endDate, notes } = req.body

    const existing = await prisma.trip.findUnique({
      where: { id }
    })

    if (!existing) {
      return res.status(404).json({ message: "Trip not found" })
    }

    if (!title || title.trim() === "") {
      return res.status(400).json({ message: "Title is required" })
    }

    if (!destination || destination.trim() === "") {
      return res.status(400).json({ message: "Destination is required" })
    }

    const updated = await prisma.trip.update({
      where: { id },
      data: {
        title: title.trim(),
        destination: destination.trim(),
        startDate: startDate || null,
        endDate: endDate || null,
        notes: notes || null
      }
    })

    console.log("PUT /api/trips/" + id)
    res.status(200).json(updated)
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Server error" })
  }
})

// DELETE trip
app.delete("/api/trips/:id", async (req, res) => {
  try {
    const id = Number(req.params.id)

    const existing = await prisma.trip.findUnique({
      where: { id }
    })

    if (!existing) {
      return res.status(404).json({ message: "Trip not found" })
    }

    await prisma.trip.delete({
      where: { id }
    })

    console.log("DELETE /api/trips/" + id)
    res.status(200).json({ message: "Trip deleted" })
  } catch (error) {
    console.error(error)
    res.status(500).json({ message: "Server error" })
  }
})

const PORT = process.env.PORT || 5000

app.listen(PORT, () => {
  console.log(`Server running on port ${PORT}`)
})
