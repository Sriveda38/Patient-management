import Sidebar from "./Sidebar";
import { Outlet } from "react-router-dom";
import "../pages/Dashboard.css";

function Layouts({ children }: { children?: React.ReactNode }) {
  return (
    <div className="dashboard-container">

      <Sidebar />

      <div className="main-content">
        <Outlet />
      </div>

    </div>
  );
}

export default Layouts;