import { Route, BrowserRouter as Router, Routes } from "react-router-dom";
import "./App.css";
import AIChat from "./pages/ai";
import Dashboard from "./pages/dashboard";

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<Dashboard />} />
        <Route path="/ai" element={<AIChat />} />
      </Routes>
    </Router>
  );
}

export default App;
