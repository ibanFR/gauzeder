import { useEffect, useState } from 'react'
import axios from 'axios'

/**
 * Root application component.
 *
 * Calls GET /api/hello on mount and displays:
 *  - A loading spinner while the request is in flight
 *  - The greeting message from the backend on success
 *  - An error message if the request fails
 */
function App() {
  const [message, setMessage] = useState(null)
  const [loading, setLoading] = useState(true)
  const [error, setError] = useState(null)

  useEffect(() => {
    axios
      .get('/api/hello')
      .then((response) => {
        setMessage(response.data.message)
      })
      .catch((err) => {
        setError(
          err.response
            ? `Server error ${err.response.status}: ${err.response.statusText}`
            : 'Could not reach the backend. Is WildFly running?',
        )
      })
      .finally(() => {
        setLoading(false)
      })
  }, [])

  return (
    <main className="app-container">
      <h1 className="app-title">Gauzeder</h1>

      {loading && (
        <div className="status loading">
          <span className="spinner" aria-hidden="true" />
          <span>Loading greeting…</span>
        </div>
      )}

      {!loading && error && (
        <div className="status error" role="alert">
          <strong>Error:</strong> {error}
        </div>
      )}

      {!loading && message && (
        <div className="status success" role="status">
          <p className="greeting">{message}</p>
          <p className="stack-note">
            React → JAX-RS → CDI → JPA → PostgreSQL
          </p>
        </div>
      )}
    </main>
  )
}

export default App
