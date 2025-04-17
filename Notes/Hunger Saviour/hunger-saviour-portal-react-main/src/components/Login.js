import FloatingLabel from 'react-bootstrap/FloatingLabel';
import Form from 'react-bootstrap/Form';
import { useRef, useState } from 'react';
import Button from 'react-bootstrap/Button';
import Card from 'react-bootstrap/Card';
import axios from 'axios';
import { useNavigate } from 'react-router-dom';
import { useUsernameContext } from '../UsernameContext'; // Import the useUsernameContext hook


function Login({ handleLogin }) {
    const emailRef = useRef();
    const passwordRef = useRef();
    const navigate = useNavigate();
    const [validated, setValidated] = useState(false);
    const { setUsername } = useUsernameContext();

    const handleSubmit = (event) => {
        event.preventDefault();
        const form = event.currentTarget;
        if (form.checkValidity() === false) {
            event.stopPropagation();
        } else {
            axios.post('http://localhost:8765/users/login',{
                username: emailRef.current.value,
                password: passwordRef.current.value
            })
            .then(res => {
                if(res.status === 200){
                    handleLogin();
                    setUsername(emailRef.current.value);
                    navigate('/restaurants'); // Navigate to /restaurants route after successful login
                }
            })
            .catch(error => {
                console.error('Login failed:', error);
                // Display an error message to the user
                // For example, you can set a state variable to store the error message and render it in the UI
            });
        }
        setValidated(true);
    };
       
    return (
        <div className="d-flex justify-content-center align-items-center min-vh-100 overflow-hidden"> {/* Add the "overflow-hidden" class */}
            <div className="text-center">
                <Card className='shadow' style={{ width: '500px' }}>
                    <Card.Header className="text-center" style={{ border: 'none', background: 'none' }}>
                        <div className='d-flex flex-column'>
                            <div className="header-image" style={{ width: '50px', height: '50px', borderRadius: '50%', backgroundImage: `url('https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRPp1ohLNn2RGU2lTyfORSITnkv7U8Jci4oCa-5T1DfFc5JpagoQkZ6imLo-_vrE3aJw10&usqp=CAU')`, backgroundSize: 'cover', margin: 'auto' }}></div>
                            <Card.Title className="fw-bold text-muted mt-1">Hunger Saviour Portal</Card.Title>
                            <Card.Subtitle className="text-center text-secondary"><small>Delivering Happiness</small></Card.Subtitle>
                        </div>
                    </Card.Header>
                    <Card.Body>
                        <Form noValidate validated={validated} onSubmit={handleSubmit}>
                            <Form.Group>
                                <FloatingLabel controlId="floatingInput" label="Email address">
                                    <Form.Control type="email" required placeholder="name@example.com" ref={emailRef} style={{ boxShadow: 'none',height: '20px' , borderRadius: '0', outline: 'none'}} />
                                    <Form.Control.Feedback type="invalid">
                                        Please enter your email.
                                    </Form.Control.Feedback>
                                </FloatingLabel>
                            </Form.Group>
                            <Form.Group className="mt-2">
                                <FloatingLabel controlId="floatingInput1" label="Password">
                                    <Form.Control type="password" required placeholder="password" ref={passwordRef} style={{ boxShadow: 'none',height: '20px' , borderRadius: '0', outline: 'none'}} />
                                    <Form.Control.Feedback type="invalid">
                                        Please enter your password.
                                    </Form.Control.Feedback>
                                </FloatingLabel>
                            </Form.Group>
                            <div className="mt-3 float-end">
                                <Button className="btn btn-success" type="submit" style={{ backgroundColor: '#FC8019', borderColor: '#FC8019' }}>Login To Portal</Button>
                            </div>
                        </Form>
                    </Card.Body>
                </Card>
            </div>
        </div>
    );
}

export default Login;
