import React, { useState } from "react";
import { Line } from "react-chartjs-2";
import {
    Chart as ChartJS,
    CategoryScale,
    LinearScale,
    PointElement,
    LineElement
} from "chart.js";

ChartJS.register(CategoryScale, LinearScale, PointElement, LineElement);

function App() {

    const [file, setFile] = useState(null);
    const [lapAnalysis, setLapAnalysis] = useState(null);

    const [lapChart, setLapChart] = useState(null);
    const [sectorChart, setSectorChart] = useState(null);
    const [speedChart, setSpeedChart] = useState(null);

    const uploadFile = async () => {
        if (!file) return;

        const formData = new FormData();
        formData.append("file", file);

        const apiBaseUrl = (process.env.REACT_APP_API_BASE_URL || "").replace(/\/$/, "");
        const res = await fetch(`${apiBaseUrl}/api/laps/analyze`, {
            method: "POST",
            body: formData
        });

        if (!res.ok) {
            const text = await res.text().catch(() => "");
            throw new Error(`Upload failed (${res.status}): ${text}`);
        }

        const data = await res.json();
        setLapAnalysis(data);

        // 🏁 Lap times
        setLapChart({
            labels: data.lapNumbers,
            datasets: [
                {
                    label: "Lap Times",
                    data: data.lapTimes,
                    borderColor: "purple"
                }
            ]
        });

        // 🟣 Sectors
        setSectorChart({
            labels: data.lapNumbers,
            datasets: [
                {
                    label: "Sector 1",
                    data: data.sector1,
                    borderColor: "blue"
                },
                {
                    label: "Sector 2",
                    data: data.sector2,
                    borderColor: "green"
                },
                {
                    label: "Sector 3",
                    data: data.sector3,
                    borderColor: "orange"
                }
            ]
        });

        // 🚀 Speeds
        setSpeedChart({
            labels: data.lapNumbers,
            datasets: [
                {
                    label: "I1 Speed",
                    data: data.i1Speeds,
                    borderColor: "cyan",
                    tension: 0.3
                },
                {
                    label: "I2 Speed",
                    data: data.i2Speeds,
                    borderColor: "yellow",
                    tension: 0.3
                }
            ]
        });
    };

    return (
        <div style={{ padding: 40 }}>
            <h1>Motorsport Lap Analyzer</h1>

            <input type="file" onChange={(e) => setFile(e.target.files[0])} />
            <br /><br />
            <button onClick={uploadFile}>Upload CSV</button>

            {lapAnalysis && (
                <div>
                    <h2>Lap Analysis</h2>
                    <p>Fastest Lap: {lapAnalysis.fastestLap}</p>
                    <p>Average Lap: {lapAnalysis.averageLap}</p>
                    <p>Best S1: {lapAnalysis.bestSector1}</p>
                    <p>Best S2: {lapAnalysis.bestSector2}</p>
                    <p>Best S3: {lapAnalysis.bestSector3}</p>
                </div>
            )}

            {lapChart && <Line data={lapChart} />}
            {sectorChart && <Line data={sectorChart} />}
            {speedChart && <Line data={speedChart} />}
        </div>
    );
}

export default App;