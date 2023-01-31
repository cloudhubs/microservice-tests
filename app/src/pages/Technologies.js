import "./styles.css"

export default function Technologies() {
    return (<>
    <h1 className="header">Technologies Used</h1>
    <div className="containerMain">
        <ul className="listHeader"> Micro-Services
            <div className="listItem">
                <li>TrainTicket</li>
                <li>eShopOnContainers</li>
            </div>
        </ul>
        <ul className="listHeader"> Testing Tools
            <div className="listItem">
                <li>Selenium: An open-source load testing framework based on Scala, Akka, and Netty</li>
                <li>Gatling: A web browser automation testing tool.</li>
            </div>
        </ul>
    </div>
    </>)
}