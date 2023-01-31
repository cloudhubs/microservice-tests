import "./styles.css"

export default function About() {
    return (<>       
    <h1>About Our Project</h1>
    <div className="containerMain">
        <div className="aboutText"> 
            This application was created by Sheldon Smith, Ethan Robinson, Timmy Frederiksen, and Trae Stevens.  This was created for CSI 43C9, the Capstone Design Course at Baylor University.
            <br/><br/>
            The aim of this project is to develop realistic tests for selected microservice projects and work with other teams who aim at test coverage analysis or dynamic system analysis.  Our project is centered around creating stress, load, and functional tests for OSS Microservices.  Our main goals for this project are OpenTracing Logging with possible workloads, well-defined use cases, and their implementations in Selenium and Gatling, and to create business processes reconstructed from the logs.
        </div>   
    </div>    
    </>)
}