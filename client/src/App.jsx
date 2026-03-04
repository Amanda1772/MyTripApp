import { useEffect, useState } from "react"
import "./App.css"

const emptyForm = {
  title: "",
  destination: "",
  startDate: "",
  endDate: "",
  notes: ""
}

export default function App() {
  const API = import.meta.env.VITE_API_URL

  const [trips, setTrips] = useState([])
  const [form, setForm] = useState(emptyForm)
  const [editingId, setEditingId] = useState(null)
  const [error, setError] = useState("")
  const [loading, setLoading] = useState(false)

  const loadTrips = async () => {
    setLoading(true)
    setError("")
    try {
      const res = await fetch(`${API}/api/trips`)
      const data = await res.json()
      if (!res.ok) throw new Error(data?.message || "Failed to load trips")
      setTrips(data)
    } catch (e) {
      setError(e.message)
    } finally {
      setLoading(false)
    }
  }

  useEffect(() => {
    loadTrips()
  }, [])

  const onChange = (e) => {
    setForm((prev) => ({ ...prev, [e.target.name]: e.target.value }))
  }

  const startEdit = (trip) => {
    setEditingId(trip.id)
    setError("")
    setForm({
      title: trip.title || "",
      destination: trip.destination || "",
      startDate: trip.startDate || "",
      endDate: trip.endDate || "",
      notes: trip.notes || ""
    })
  }

  const cancelEdit = () => {
    setEditingId(null)
    setError("")
    setForm(emptyForm)
  }

  const submit = async (e) => {
    e.preventDefault()
    setError("")

    if (!form.title.trim()) {
      setError("Title is required")
      return
    }

    if (!form.destination.trim()) {
      setError("Destination is required")
      return
    }

    try {
      const isEdit = editingId !== null
      const url = isEdit ? `${API}/api/trips/${editingId}` : `${API}/api/trips`
      const method = isEdit ? "PUT" : "POST"

      const res = await fetch(url, {
        method,
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify({
          title: form.title,
          destination: form.destination,
          startDate: form.startDate || null,
          endDate: form.endDate || null,
          notes: form.notes || null
        })
      })

      const data = await res.json()
      if (!res.ok) throw new Error(data?.message || "Request failed")

      cancelEdit()
      await loadTrips()
    } catch (e2) {
      setError(e2.message)
    }
  }

  const removeTrip = async (id) => {
    const ok = window.confirm("Are you sure you want to delete this trip?")
    if (!ok) return

    setError("")
    try {
      const res = await fetch(`${API}/api/trips/${id}`, { method: "DELETE" })
      const data = await res.json()
      if (!res.ok) throw new Error(data?.message || "Delete failed")
      await loadTrips()
    } catch (e) {
      setError(e.message)
    }
  }

  return (
    <div style={{ maxWidth: 950, margin: "0 auto", padding: 20 }}>
      <h1>MyTripApp Trips</h1>

      <p style={{ marginTop: 6 }}>
        Week 4 core feature: Trips CRUD connected React, API, and database
      </p>

      {error && (
        <div style={{ padding: 12, border: "1px solid", marginTop: 12 }}>
          {error}
        </div>
      )}

      <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 18, marginTop: 18 }}>
        <div style={{ border: "1px solid", padding: 16 }}>
          <h2>{editingId ? "Edit Trip" : "Create Trip"}</h2>

          <form onSubmit={submit}>
            <div style={{ marginBottom: 10 }}>
              <label>Title</label>
              <input
                name="title"
                value={form.title}
                onChange={onChange}
                placeholder="Spring break trip"
                style={{ width: "100%", padding: 8 }}
              />
            </div>

            <div style={{ marginBottom: 10 }}>
              <label>Destination</label>
              <input
                name="destination"
                value={form.destination}
                onChange={onChange}
                placeholder="Hawaii"
                style={{ width: "100%", padding: 8 }}
              />
            </div>

            <div style={{ display: "grid", gridTemplateColumns: "1fr 1fr", gap: 10 }}>
              <div style={{ marginBottom: 10 }}>
                <label>Start date</label>
                <input
                  name="startDate"
                  value={form.startDate}
                  onChange={onChange}
                  placeholder="2026-04-01"
                  style={{ width: "100%", padding: 8 }}
                />
              </div>

              <div style={{ marginBottom: 10 }}>
                <label>End date</label>
                <input
                  name="endDate"
                  value={form.endDate}
                  onChange={onChange}
                  placeholder="2026-04-08"
                  style={{ width: "100%", padding: 8 }}
                />
              </div>
            </div>

            <div style={{ marginBottom: 10 }}>
              <label>Notes</label>
              <textarea
                name="notes"
                value={form.notes}
                onChange={onChange}
                placeholder="Things I want to do"
                style={{ width: "100%", padding: 8, minHeight: 90 }}
              />
            </div>

            <div style={{ display: "flex", gap: 10 }}>
              <button type="submit" style={{ padding: "10px 14px" }}>
                {editingId ? "Save Update" : "Create Trip"}
              </button>

              {editingId && (
                <button type="button" onClick={cancelEdit} style={{ padding: "10px 14px" }}>
                  Cancel
                </button>
              )}
            </div>
          </form>
        </div>

        <div style={{ border: "1px solid", padding: 16 }}>
          <h2>Trips List</h2>

          <button onClick={loadTrips} style={{ padding: "8px 12px" }}>
            Refresh
          </button>

          {loading ? (
            <p>Loading…</p>
          ) : trips.length === 0 ? (
            <p>No trips yet</p>
          ) : (
            <div style={{ marginTop: 12, display: "grid", gap: 10 }}>
              {trips.map((t) => (
                <div key={t.id} style={{ border: "1px solid", padding: 12 }}>
                  <div style={{ display: "flex", justifyContent: "space-between", gap: 12 }}>
                    <div>
                      <div style={{ fontWeight: 700 }}>{t.title}</div>
                      <div>Destination: {t.destination}</div>
                      <div>Start: {t.startDate || "None"}</div>
                      <div>End: {t.endDate || "None"}</div>
                      {t.notes && <div>Notes: {t.notes}</div>}
                      <div style={{ fontSize: 12, marginTop: 6 }}>
                        ID: {t.id}
                      </div>
                    </div>

                    <div style={{ display: "flex", flexDirection: "column", gap: 8 }}>
                      <button onClick={() => startEdit(t)} style={{ padding: "8px 12px" }}>
                        Edit
                      </button>
                      <button onClick={() => removeTrip(t.id)} style={{ padding: "8px 12px" }}>
                        Delete
                      </button>
                    </div>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>

      <div style={{ marginTop: 18, fontSize: 12 }}>
        API base: {API}
      </div>
    </div>
  )
}
