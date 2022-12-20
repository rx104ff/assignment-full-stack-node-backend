import './App.css';
import axios from 'axios';
import React, { useEffect, useState } from 'react';

import 'bootstrap/dist/css/bootstrap.min.css';
import Button from 'react-bootstrap/Button';
import InputGroup from 'react-bootstrap/InputGroup';
import Form from 'react-bootstrap/Form';
import Modal from 'react-bootstrap/Modal';


function EmployeeList() {
  const [employeesState, setEmployeesState] = useState([]);
  const [showAddModalState, setShowAddModalState] = useState(false);
  const [showEditModalState, setShowEditModalState] = useState(false);

  const [editIdState, setEditIdState] = useState();
  const [editFirstnameState, setEditFirstnameState] = useState("");
  const [editLastnameState, setEditLastnameState] = useState("");
  const [editSalaryState, setEditSalaryState] = useState();

  const [addFirstnameState, setAddFirstnameState] = useState("");
  const [addLastnameState, setAddLastnameState] = useState("");
  const [addSalaryState, setAddSalaryState] = useState();

  const [modalErrorsState, setModalErrorsState] = useState([])

  const getEmployees = async () => {
    const result = await axios.get(
      `http://localhost:8080/api/employee/all`
    ).catch(function (error) {
      if (error.response) {
        console.log(error.data);
      } else if (error.request) {
        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
    });
    
    setEmployeesState(result.data);
  };

  useEffect(() => {
    getEmployees();
  }, []);

  const handleAdd = async () => {
    setModalErrorsState([]);
    
    const result = await axios.post(
      `http://localhost:8080/api/employee`,
      {
        firstname: addFirstnameState,
        lastname: addLastnameState,
        salary: addSalaryState
      }
    ).catch(function (error) {
      if (error.response) {

        // Transform the violations list into a dictionary
        const errors = Object.fromEntries(
          error.response.data.violations.map(
            violation => [violation.fieldName, violation.message]
          )
        );
        setModalErrorsState(errors);
      } else if (error.request) {
        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
    });

    if (result.status === 200) {
      getEmployees();
      handleAddModalCancel();
    }
  }

  const handleEdit = async () => {
    setModalErrorsState([]);

    const result = await axios.put(
      `http://localhost:8080/api/employee`,
      {
        id: editIdState,
        firstname: editFirstnameState,
        lastname: editLastnameState,
        salary: editSalaryState
      }
    ).catch(function (error) {
      if (error.response) {

        // Transform the violations list into a dictionary
        const errors = Object.fromEntries(
          error.response.data.violations.map(
            violation => [violation.fieldName, violation.message]
          )
        );
        setModalErrorsState(errors);
      } else if (error.request) {
        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
    });

    if (result.status === 200) {
      getEmployees();
      handleEditModalCancel();
    }
  }

  const handleDelete = async (employee) => {
    setModalErrorsState([]);

    const result = await axios.delete(
      `http://localhost:8080/api/employee?id=${employee.id}`
    ).catch(function (error) {
      if (error.response) {
        console.log(error.data);
      } else if (error.request) {
        console.log(error.request);
      } else {
        console.log('Error', error.message);
      }
    });

    if (result.status === 200) {
      getEmployees();
    }
  }

  const handleAddModalCancel = () => {
    setAddFirstnameState("");
    setAddLastnameState("");
    setAddSalaryState();
    setModalErrorsState([]);
    setShowAddModalState(false);
  };

  const handleEditModalCancel = () => {
    setShowEditModalState(false);
    setEditFirstnameState("");
    setEditLastnameState("");
    setModalErrorsState([]);
    setEditSalaryState();
  };

  const showEditModal = (employee) => {
    setEditIdState(employee.id);
    setEditFirstnameState(employee.firstname);
    setEditLastnameState(employee.lastname);
    setEditSalaryState(employee.salary);
    setShowEditModalState(true);
  }

  const showAddModal = () => {
    setShowAddModalState(true);
  }

  return (
    <div className="EmployeeList">
      <Modal show={showAddModalState} onHide={handleAddModalCancel}>
        <Modal.Header closeButton>
          <Modal.Title>Add Employee</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <>
          <InputGroup className="mb-3">
            <Form.Control
              isInvalid={Object.keys(modalErrorsState).includes("firstname")}
              placeholder="First Name"
              aria-label="Firstname"
              onChange={e => setAddFirstnameState(e.target.value)}
              value={addFirstnameState}
            />
            <Form.Control.Feedback type="invalid">
              {modalErrorsState.firstname}
            </Form.Control.Feedback>
          </InputGroup>

          <InputGroup className="mb-3">
            <Form.Control
              isInvalid={Object.keys(modalErrorsState).includes("lastname")}
              placeholder="Last Name"
              aria-label="Lastname"
              onChange={e => setAddLastnameState(e.target.value)}
              value={addLastnameState}
            />
            <Form.Control.Feedback type="invalid">
              {modalErrorsState.lastname}
            </Form.Control.Feedback>
          </InputGroup>

          <InputGroup className="mb-3">
            <InputGroup.Text>$</InputGroup.Text>
            <Form.Control 
              isInvalid={Object.keys(modalErrorsState).includes("salary")}
              aria-label="Salary"
              onChange={e => setAddSalaryState(e.target.value)}
              value={addSalaryState}
            />
            <Form.Control.Feedback type="invalid">
              {modalErrorsState.salary}
            </Form.Control.Feedback>
          </InputGroup>
        </>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleAddModalCancel}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleAdd}>
            Add
          </Button>
        </Modal.Footer>
      </Modal>

      <Modal show={showEditModalState} onHide={handleEditModalCancel}>
        <Modal.Header closeButton>
          <Modal.Title>Edit Employee</Modal.Title>
        </Modal.Header>
        <Modal.Body>
        <>
          <InputGroup className="mb-3">
            <Form.Control
              isInvalid={Object.keys(modalErrorsState).includes("firstname")}
              placeholder="First Name"
              aria-label="Firstname"
              onChange={e => setEditFirstnameState(e.target.value)}
              value={editFirstnameState}
            />
            <Form.Control.Feedback type="invalid">
              {modalErrorsState.firstname}
            </Form.Control.Feedback>
          </InputGroup>

          <InputGroup className="mb-3">
            <Form.Control
              isInvalid={Object.keys(modalErrorsState).includes("lastname")}
              placeholder="Last Name"
              aria-label="Lastname"
              onChange={e => setEditLastnameState(e.target.value)}
              value={editLastnameState}
            />
            <Form.Control.Feedback type="invalid">
              {modalErrorsState.lastname}
            </Form.Control.Feedback>
          </InputGroup>

          <InputGroup className="mb-3">
            <InputGroup.Text>$</InputGroup.Text>
            <Form.Control
              isInvalid={Object.keys(modalErrorsState).includes("salary")}
              aria-label="Salary"
              onChange={e => setEditSalaryState(e.target.value)}
              value={editSalaryState}
            />
            <Form.Control.Feedback type="invalid">
              {modalErrorsState.salary}
            </Form.Control.Feedback>
          </InputGroup>
        </>
        </Modal.Body>
        <Modal.Footer>
          <Button variant="secondary" onClick={handleEditModalCancel}>
            Cancel
          </Button>
          <Button variant="primary" onClick={handleEdit}>
            Edit
          </Button>
        </Modal.Footer>
      </Modal>
    <h2>EMPLOYEES</h2>
    <br />
    <table className="table table-borderless table-condensed">
      <thead className="border-bottom">
        <tr>
          <th>First Name</th>
          <th>Last Name</th>
          <th>Salary</th>
          <th></th>
        </tr>
      </thead>
      <hr/>
      <tbody>
        {employeesState &&
          employeesState.map((employee) => {
            return (
              <>
                <tr key={employee.id}>
                  <td>
                    {employee.firstname}
                  </td>
                  <td>{employee.lastname}</td>
                  <td>$ {employee.salary.toLocaleString()}</td>
                  <td>
                    <Button variant="outline-secondary" onClick={() => { showEditModal(employee) }}>Edit</Button>{' '}
                    <Button variant="outline-danger" onClick={() => { handleDelete(employee) }}>Delete</Button>{' '}
                  </td>
                </tr>
              </>
            );
          })}
            <>
                <tr>
                  <td></td>
                  <td></td>
                  <td></td>
                  <td>
                    <Button variant="success" onClick={showAddModal}>Add Employee</Button>{' '}
                  </td>
                </tr>
            </>
      </tbody>
    </table>
  </div>
  )
}

function App() {
  return (
    <div className="App">
      <EmployeeList></EmployeeList>
    </div>
  );
}

export default App;
