import { useNavigate } from "react-router-dom";
import { Button, List, ListItemButton, ListItemText } from "@mui/material";

function Sidebar() {
  const navigate = useNavigate();

  const handleLogout = () => {
    localStorage.removeItem("token");
    navigate("/login");
  };

  return (
    <div
      className="sidebar"
      style={{
        display: "flex",
        flexDirection: "column",
        height: "100vh",
        padding: "16px",
      }}
    >
      

      <List>
        <ListItemButton onClick={() => navigate("/dashboard")}>
          <ListItemText primary="Dashboard" />
        </ListItemButton>

        <ListItemButton onClick={() => navigate("/patient")}>
          <ListItemText primary="Patient Management" />
        </ListItemButton>
      </List>

      <Button
        onClick={handleLogout}
        variant="contained"
        fullWidth
        sx={{
          mt: 75,
          background:
            "linear-gradient(90deg, #36D1DC, #5B86E5, #C86DD7)",
          borderRadius: "30px",
          fontWeight: "bold",
          textTransform: "none",
        }}
      >
        LOGOUT
      </Button>
    </div>
  );
}

export default Sidebar;