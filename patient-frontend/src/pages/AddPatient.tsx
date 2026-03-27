import { useState } from "react";
import axios from "axios";
import {
    Container,
    TextField,
    Button,
    Typography,
    Paper,
    Grid,
} from "@mui/material";

const AddPatient = () => {
     const token = localStorage.getItem("token");
    const [formData, setFormData] = useState({
        patientName: "",
        email: "",
        password: "",
        role: ""
    });

    const handleChange = (e: any) => {
        setFormData({
            ...formData,
            [e.target.name]: e.target.value,
        });
    };

    const handleSubmit = async (e: any) => {
        e.preventDefault();

        try {
            const response = await axios.post(
                "http://localhost:8080/admin/add",
                formData,
                {
                    headers: {
                        Authorization: `Bearer ${token}`,
                    },
                }
            );
            alert("Patient saved successfully!");
            console.log(response.data);         
            setFormData({
                patientName: "",
                email: "",
                password: "",
                role: ""
            });
        } catch (error) {
            console.error(error);
            alert("Error saving patient");
        }
    };

    return (
        <Container maxWidth="sm">
            <Paper elevation={4} sx={{ padding: 4, marginTop: 5 }}>
                <Typography variant="h5" gutterBottom align="center">
                   Add Patient 
                </Typography>

                <form onSubmit={handleSubmit}>
                    <Grid container spacing={2}>
                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Name"
                                name="patientName"
                                value={formData.patientName}
                                onChange={handleChange}
                                required
                            />
                        </Grid>

                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Email"
                                name="email"
                                type="email"
                                value={formData.email}
                                onChange={handleChange}
                                required
                            />
                        </Grid>

                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Password"
                                name="password"
                                value={formData.password}
                                onChange={handleChange}
                                required
                            />
                        </Grid>

                        <Grid item xs={12}>
                            <TextField
                                fullWidth
                                label="Role"
                                name="role"
                                value={formData.role}
                                onChange={handleChange}
                                required
                            />
                        </Grid>

                        <Grid item xs={12}>
                            <Button
                                type="submit"
                                variant="contained"
                                fullWidth
                                sx={{
                                    mt: 4,
                                    background:
                                        "linear-gradient(90deg, #36D1DC, #5B86E5, #C86DD7)",
                                    borderRadius: "30px",
                                    fontWeight: "bold",
                                    textTransform: "none",
                                }}
                            >
                                Save Patient
                            </Button>
                        </Grid>
                    </Grid>
                </form>
            </Paper>
        </Container>
    );
};

export default AddPatient;