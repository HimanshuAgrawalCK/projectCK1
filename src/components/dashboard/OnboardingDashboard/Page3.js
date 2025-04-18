import constantsOfpage3 from "./constants/constantsOfPage3"
import React from "react"
import "../../../styles/Page3.css"

export default function Page3({ formData }) {
    return (<>
    <div className="heading_section">
            <h1>Create Cost and Usage Report</h1>
            <h2>
            Create a Cost & Usage Report by following these steps
            </h2>
          </div>
          <div className="steps_wrapper">
            <ol>
              {constantsOfpage3.map((step, index) => (
                <li key={index + Math.random}>{step}</li>
              ))}
            </ol>
          </div>
    </>)
}