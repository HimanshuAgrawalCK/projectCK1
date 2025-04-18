import constantsOfPage2 from "./constants/constantsOfPage2";

export default function Page2() {
    return (<>
    <div className="heading_section">
            <h1>Add Customer Managed Policies</h1>
            <h2>
              Create an Inline Policy for the role by following these steps
            </h2>
          </div>
          <div className="steps_wrapper">
            <ol>
              {constantsOfPage2.map((step, index) => (
                <li key={index + Math.random}>{step}</li>
              ))}
            </ol>
          </div>
    </>)
}