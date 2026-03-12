import React, { useState } from "react";

function App() {

  const [file, setFile] = useState(null);
  const [analysis, setAnalysis] = useState(null);

  const uploadFile = async () => {
    const formData = new FormData();
    formData.append("file", file);

    const response = await fetch("http://localhost:8081/api/telemetry/analyze", {
      method: "POST",
      body: formData
    });

    const data = await response.json();
    setAnalysis(data);
  };

  return (
      <div style={{padding:40}}>
        <h1>Telemetry Analyzer</h1>

        <input
            type="file"
            onChange={(e) => setFile(e.target.files[0])}
        />

        <br/><br/>

        <button onClick={uploadFile}>
          Upload CSV
        </button>

        {analysis && (
            <div style={{marginTop:30}}>
              <h2>Analysis</h2>

              <p>Max Speed: {analysis.maxSpeed}</p>
              <p>Avg Speed: {analysis.avgSpeed}</p>
              <p>Avg Throttle: {analysis.avgThrottle}</p>
              <p>Avg Brake: {analysis.avgBrake}</p>
              <p>Gear Shifts: {analysis.gearShifts}</p>
            </div>
        )}
      </div>
  );
}

export default App;