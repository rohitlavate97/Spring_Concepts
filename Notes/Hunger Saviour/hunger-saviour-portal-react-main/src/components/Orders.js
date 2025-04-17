import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useUsernameContext } from '../UsernameContext';
import { DataGrid } from '@mui/x-data-grid';
import CheckCircleSharpIcon from '@mui/icons-material/CheckCircleSharp';
import '../index.css';

function Orders() {
    const { username } = useUsernameContext();
    const [orders, setOrders] = useState([]);

    useEffect(() => {
        axios.get(`http://localhost:8765/orders/${username}`)
            .then(res => {
                setOrders(res.data);
            })
            .catch(error => {
                console.error('Error fetching orders:', error);
            });
    }, [username]); // Make sure to include username in the dependency array

    const columns = [
        { field: 'orderId', headerName: 'Order ID', flex: 1, headerClassName: 'header-cell' },
        { field: 'transactionId', headerName: 'Transaction ID', flex: 1, headerClassName: 'header-cell' },
        { field: 'userName', headerName: 'Username', flex: 1, headerClassName: 'header-cell' },
        { field: 'amountPaid', headerName: 'Amount Paid', flex: 1, headerClassName: 'header-cell' },
        { field: 'orderCreatedTime', headerName: 'Order Created Time', flex: 1, headerClassName: 'header-cell' },
        { field: 'orderStatus', headerName: 'Order Status', flex: 1,
            headerClassName: 'header-cell',
            renderCell: (params) => (
                <span>
                    {params.value === 'Completed' ? <CheckCircleSharpIcon style={{ color: 'green' }} /> : <CheckCircleSharpIcon style={{ color: 'green' }} />}
                    {params.value}
                </span>
            )
        },
    ];

    const getRowId = (row) => row.orderId;

    return (
        <div className='container mt-5 pt-5 mb-5' style={{ width: '100%' }}>
            <DataGrid
                rows={orders}
                columns={columns}
                pageSize={5}
                rowsPerPageOptions={[5, 10, 20]}
                checkboxSelection
                getRowId={getRowId}
            />
        </div>
    );
}

export default Orders;
