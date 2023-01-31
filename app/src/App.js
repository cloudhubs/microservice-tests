import Navbar from "./Navbar";
import Technologies from "./pages/Technologies";
import Home from "./pages/Home";
import About from "./pages/About";

 
function App() {
  let Component
  switch(window.location.pathname){
    case "/":
      Component = Home
      break;
    case "/technologies":
      Component = Technologies
      break;
    case "/about":
      Component = About
      break;
  }
  return (<>
    <Navbar/>
    <div className="container">
      <Component/>
    </div>
  </>
  )

}
  
export default App;