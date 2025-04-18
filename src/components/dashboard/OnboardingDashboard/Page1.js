import constantsOfPage1 from "./constants/constantsOfPage1";


export default function Page1({ formData, setFormData }) {
    const handleChange = (e) => {
      const { name, value } = e.target;
      setFormData((prev) => ({
        ...prev,
        [name]: value
      }));
    };
  
    return (
      <>
        <div className="heading_section">
          <h1>I AM ROLE USER</h1>
          <h2>Create an IAM Role by following these steps</h2>
        </div>
  
        <div className="steps_wrapper">
          <ol>
            {constantsOfPage1.map((step, index) => (
              <li key={index+Math.random}>{step}</li>
            ))}
          </ol>
  
          {/* Form */}
            <h3>Enter IAM Role Details</h3>
          <form className="iam-form">
  
            <div className="form-group">
              <label htmlFor="accountId">Account ID</label>
              <input
                type="text"
                id="accountId"
                name="accountId"
                value={formData.accountId}
                onChange={handleChange}
                required
              />
            </div>
  
            <div className="form-group">
              <label htmlFor="accountName">Account Name</label>
              <input
                type="text"
                id="accountName"
                name="accountName"
                value={formData.accountName}
                onChange={handleChange}
                required
              />
            </div>
  
            <div className="form-group">
              <label htmlFor="arn">Role ARN</label>
              <input
                type="text"
                id="arn"
                name="arn"
                value={formData.arn}
                onChange={handleChange}
                required
              />
            </div>
          </form>
        </div>
      </>
    );
  }
  
