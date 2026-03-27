import  { useEffect, useState, useMemo } from "react";
import axios from "axios";
import {
  MaterialReactTable,
  useMaterialReactTable,
  type MRT_ColumnDef,
} from 'material-react-table';
import { Button } from "@mui/material";
import { useNavigate } from "react-router-dom";
import AddPatient from "./AddPatient";


type Patients = {
  id: number;
  patientName: string;
  email: string;
};

const Patient = () => {
   const navigate = useNavigate();
   const [patients, setPatients] = useState<Patients[]>([]);
 
   function fetchPaitents() {
  const token = localStorage.getItem("token"); 

  axios.get("http://localhost:8080/user/getAllPatients", {
    headers: {
      Authorization: `Bearer ${token}`, 
    },
  })
  .then((response) => {
    setPatients(response.data);
    console.log(response.data);
  })
  .catch((error) => {
    console.error("Error fetching patients:", error);
  });
}
    useEffect(()=>{
        fetchPaitents()

    }, [])

    console.log(patients)

   

  const handleDelete = async (id: number) => {
  const token = localStorage.getItem("token");

  try {
    await axios.delete(`http://localhost:8080/admin/delete/${id}`, {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    });
    setPatients((prev) => prev.filter((p) => p.id !== id));
     fetchPaitents();

  } catch (error) {
    console.error("Delete failed:", error);
    alert("Not authorized or error occurred");
  }
};

const handleEdit = (patient: Patients) => {
  const newName = prompt("Enter new name", patient.patientName);

  if (!newName) return;

  const token = localStorage.getItem("token");

  axios.put(
    `http://localhost:8080/admin/update/${patient.id}`,
    { ...patient, patientName: newName },
    {
      headers: {
        Authorization: `Bearer ${token}`,
      },
    }
  )
  .then(() => {
    fetchPaitents(); 
  })
  .catch((err) => {
    console.error(err);
    alert("Edit failed");
  });
};

const role = localStorage.getItem("role");
  

  const columns = useMemo<MRT_ColumnDef<Patients>[]>(
    () => [
      {
        accessorKey: 'id', 
        header: 'ID',
        size: 200,
      },

      {
        accessorKey: 'patientName', 
        header: 'Name',
        size: 150,
      },

      {
        accessorKey: 'email',
        header: 'Email',
        size: 150,
      },
     
   {
      header: 'Actions',
      Cell: ({ row }) => {
        const patient = row.original;

        return (
          <>
            {role === "ADMIN" && (
              <>
              <Button style={{ marginRight: "10px", color: "#1976d2" }} onClick={() => navigate("/addPatient")}>
                  Add
                </Button>

                <Button style={{ marginRight: "10px", color: "#1976d2" }} onClick={() => handleEdit(patient)}>
                  Edit
                </Button>

                <Button style={{ color: "red" }} onClick={() => handleDelete(patient.id)}>
                  Delete
                </Button>
              </>
            )}
          </>
        );
      },
    },
  ],
  [role]
);
 

 
  const table = useMaterialReactTable({
    columns,
    data:patients, 
  });

  return <MaterialReactTable table={table} />;
};

export default Patient;
