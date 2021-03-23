
import { Container, Grid, InputAdornment, MenuItem, Paper, Snackbar, TextField, Typography } from '@material-ui/core';
import Modal from 'antd/lib/modal/Modal';
import { Button } from 'antd';
import Axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Doughnut, Line } from 'react-chartjs-2';
import { useHistory, useParams } from 'react-router-dom';
import Header from '../Header';
import "../style.css"
import Device from '../Device/Device';
import Alert from '@material-ui/lab/Alert';
import SockJS from "sockjs-client";
import Stomp from "stompjs";
const api = Axios.create({
    baseURL: 'http://localhost:8081/api/'
})
function RoomDetail(props) {
    const [room, setroom] = useState({});
    const history = useHistory();
    const [visible, setVisible] = useState(false);
    const roomId = useParams().id;
    const [newDevice, setnewDevice] = useState({ name: "", code: "", roomId: "", deviceType: "" })
    const [deviceType, setdeviceType] = useState(["Adruino",]);
    const [roomStatsList, setroomStatsList] = useState(true);
    const [visibleUpdate, setVisibleUpdate] = useState(false)
    const [roomUpdate, setRoomUpdate] = useState({})
    const [temperature, settemperature] = useState([]);
    const [listCategory, setlistCategory] = useState(["Laboratory", "Office", "Accommodation"])
    const [humidity, sethuminidity] = useState([]);
    const [times, settimes] = useState([]);
    const [listDevice, setlistDevice] = useState([]);
    const [deviceExist, setdeviceExist] = useState(true);
    const [count, setcount] = useState()
    const [alertCreated, setalertCreated] = useState(false);
    const [alertCreatedMessage, setalertCreatedMessage] = useState({ message: 'Thêm thiết bị thành công!', status: 'success' });
    const [message, setmessage] = useState();
    const [deleteModal, setdeleteModal] = useState(false);
    // useEffect(() => {
    //     const interval = setInterval(() => {
    //         console.log('This will run every second!');
    //         // setcount(Date.now);
    //     }, 2000);
    //     return () => clearInterval(interval);
    //     // socket.on('/app/topic', res => console.log(res))
    // }, [])
    useEffect(() => {
        api.get(`/room/${roomId}`).then(res => {
            console.log(res.data);
            setnewDevice({ ...newDevice, roomId: roomId });
            setroom(res.data);
            let data = res.data;
            setRoomUpdate({ name: data.name, roomType: data.roomType, length: data.length, width: data.width, height: data.height, description: data.description, url: data.url })
            if (res.data.roomStatsList === null) {
                setroomStatsList(false);
            } else {
                setlistDevice(res.data.device);
                settemperature(res.data.roomStatsList.temp);
                sethuminidity(res.data.roomStatsList.humidity);
                settimes(res.data.roomStatsList.time);
            }
            if (res.data.device.length === 0) {
                setdeviceExist(false);
            }
        })
    }, [])
    const handleDelete = () => {
        console.log(roomId);
        api.delete('/room/' + roomId).then(res =>{
            console.log(res)
            if (res.status === 200) {
                setdeleteModal(false);
                setalertCreatedMessage({...alertCreatedMessage,message: 'Xóa thành công'})
                setalertCreated(true);
                setTimeout(() => {
                    history.push('/')
                }, 1000);
            } else {
                setalertCreatedMessage({ message: 'Có lỗi xảy ra!', status: 'error' });
                setTimeout(() => {
                    history.push('/')
                }, 1000);
            }
        })
    };
    const handleSubmit = () => {
        console.log(newDevice)
        api.post(`/device`, newDevice).then(res => {
            console.log(res);
            if (res.status === 200) {
                setVisible(false);
                setalertCreated(true);
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            } else {
                setalertCreatedMessage({ message: 'Có lỗi xảy ra!', status: 'error' });
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
        })
    };
    const handleUpdate = () => {
        console.log(roomUpdate)
        api.put('/room/' + roomId,roomUpdate).then(res =>{
            console.log(res)
            if (res.status === 200) {
                setVisibleUpdate(false);
                setalertCreatedMessage({...alertCreatedMessage,message: 'Cập nhật thành công'})
                setalertCreated(true);
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            } else {
                setalertCreatedMessage({ message: 'Có lỗi xảy ra!', status: 'error' });
                setTimeout(() => {
                    window.location.reload();
                }, 1000);
            }
        })
    }
    const handleChangeUpdate = (event) => {
        setRoomUpdate((prevState) => ({
            ...prevState,
            [event.target.id]: event.target.value
        }));
    }
    const handleChange = (event) => {
        setnewDevice((prevState) => ({
            ...prevState,
            [event.target.id]: event.target.value
        }));
    }

    return (
        <div>
            <Header>
            </Header>
            <Container>
                <div>{count}</div>
                <Snackbar open={alertCreated} autoHideDuration={1000}
                    anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
                    onClose={() => setalertCreated(false)}
                >
                    <Alert variant="filled" severity={alertCreatedMessage.status}>
                        {alertCreatedMessage.message}
                    </Alert>
                </Snackbar>
                <div className="add-btn">
                    <Button style={{ marginBottom: 50, marginRight: 15}} type="secondary" onClick={() => setdeleteModal(true)}>Xóa phòng</Button>
                    <Button style={{ marginBottom: 50, marginRight: 15 }} type="primary" onClick={() => setVisibleUpdate(true)}>Cập nhật</Button>
                    <Button style={{ marginBottom: 50 }} type="primary" onClick={() => setVisible(true)}>Thêm thiết bị</Button>
                </div>
                <Modal title="Xác nhận" visible={deleteModal} onOk={() => handleDelete()} onCancel={() => setdeleteModal(false)}>
                    <p>Bạn muốn xóa phòng này chứ?</p>
                </Modal>
                <Modal
                    title="Thêm thiết bị"
                    centered
                    visible={visible}
                    onOk={(data) => handleSubmit(data)}
                    onCancel={() => setVisible(false)}
                    width={600}
                >
                    <Container>
                        <h4 style={{ marginBottom: 20 }}>Thông tin cơ bản</h4>
                        {/* <Grid container spacing={10}> */}
                        <TextField
                            required
                            style={{ marginBottom: 20 }}
                            id="code"
                            size="small"
                            onChange={(e) => handleChange(e)}
                            className="text-input"
                            label="Mã thiết bị"
                            variant="outlined"
                        />
                        <TextField
                            variant="outlined"
                            id="name"
                            // required
                            onChange={(e) => handleChange(e)}
                            size="small"
                            style={{ marginBottom: 20 }}
                            className="text-input"
                            label="Tên thiết bị"
                        >

                        </TextField>

                        <h4 style={{ marginBottom: 20 }}>Thông số kỹ thuật</h4>
                        <TextField
                            variant="outlined"
                            id="deviceType"
                            // required
                            size="small"
                            onChange={(e) => setnewDevice({ ...newDevice, deviceType: e.target.value })}
                            style={{ marginBottom: 20 }}
                            className="text-input"
                            select
                            label="Loại thiết bị"
                        // value={watch("categoryId")}
                        >
                            {deviceType.map((category, index) => (
                                <MenuItem key={index} value={deviceType[index]}>
                                    {deviceType[index]}
                                </MenuItem>
                            ))}
                        </TextField>
                        {/* </Grid> */}
                        {/* <Grid item xs={6}>
                            <TextField
                                className="text-input"
                                id="description"
                                label="Description"
                                multiline
                                rows={4}
                                variant="outlined"
                            />
                        </Grid> */}
                    </Container>
                </Modal>
                <div style={{ marginTop: 50, marginBottom: 50, marginRight: 10, marginLeft: 30 }} >
                    <Grid container spacing={6}>
                        <Grid item xs={6}>
                            <div className="img-room-container">
                                <img className="image-responsive-room-detail" src={room.url}></img>
                            </div>
                        </Grid>
                        <Grid item xs={6}>
                            <Typography gutterBottom variant="h5" component="h2">
                                Phòng : {room.name}
                            </Typography>
                            <p><strong>Loại phòng: </strong>{room.roomType}</p>
                            <p><strong>Mô tả: </strong>{room.description}</p>
                            <Typography gutterBottom variant="h5" component="h2">
                                Thông số :
                                </Typography>
                            <p><strong>Chiều dài: </strong>{room.length}</p>
                            <p><strong>Chiều rộng: </strong>{room.width}</p>
                            <p><strong>Chiều cao: </strong>{room.height}</p>
                        </Grid>
                    </Grid>
                </div>
                <div style={{ marginTop: 50, marginBottom: 50, marginRight: 10, marginLeft: 30 }}>
                    <Typography gutterBottom variant="h5" component="h2">
                        Danh sách thiết bị
                    </Typography>
                </div>
                {(!deviceExist) && (<div style={{ textAlign: 'center' }}><p>Không có thiết bị,vui lòng thêm mới thiết bị</p></div>)}
                <div style={{ marginBottom: 50, marginRight: 10, marginLeft: 80 }}>
                    {(deviceExist) && (
                        <Grid container spacing={3}>
                            {listDevice.length > 0 && listDevice.map((r, i) =>
                                <Device device={r} />
                            )}
                        </Grid>

                    )}
                </div>
                <Grid>
                    <div style={{ marginTop: 50, marginBottom: 50, marginRight: 10, marginLeft: 30 }}>
                        <Typography gutterBottom variant="h5" component="h2">
                            Báo cáo
                    </Typography>
                    </div>
                    {(!roomStatsList) && (<div style={{ textAlign: 'center' }}> <p>Không có báo cáo</p></div>)}
                    {(roomStatsList) && (
                        <Grid container spacing={1} style ={{marginLeft:20}}>
                            <Grid item xs={4}>
                                <Line
                                    data={{
                                        labels: times,
                                        datasets: [
                                            {
                                                data: temperature,
                                                label: "Nhiệt độ",
                                                borderColor: "#ff6b6b",
                                                fill: false
                                            }
                                        ]
                                    }}
                                    options={{
                                        title: {
                                            display: true,
                                            text: "Biểu đồ nhiệt độ trong ngày"
                                        },
                                        legend: {
                                            display: true,
                                            position: "bottom"
                                        }
                                    }}
                                />
                            </Grid>
                            <Grid item xs={4}>
                                <Line
                                    data={{
                                        labels: times,
                                        datasets: [
                                            {
                                                data: humidity,
                                                label: "Độ ẩm",
                                                borderColor: "#4eb7fc",
                                                fill: false
                                            }
                                        ]
                                    }}
                                    options={{
                                        title: {
                                            display: true,
                                            text: "Biểu đồ độ ẩm trong ngày"
                                        },
                                        legend: {
                                            display: true,
                                            position: "bottom"
                                        }
                                    }}
                                />
                            </Grid>
                            {/* <Grid item xs={4}>
                                <Line
                                    data={{
                                        labels: [1500, 1600, 1700, 1750, 1800, 1850, 1900, 1950, 1999, 2050],
                                        datasets: [
                                            {
                                                data: [86, 114, 106, 106, 107, 111, 133, 221, 783, 2478],
                                                label: "Nồng độ CO",
                                                borderColor: "#3e95cd",
                                                fill: false
                                            }
                                        ]
                                    }}
                                    options={{
                                        title: {
                                            display: true,
                                            text: "Biểu đồ nồng độ khí CO trong ngày"
                                        },
                                        legend: {
                                            display: true,
                                            position: "bottom"
                                        }
                                    }}
                                />
                            </Grid> */}
                        </Grid>
                    )
                    }
                </Grid>
                <Modal
                    title="Cập nhật thông tin"
                    centered
                    visible={visibleUpdate}
                    onOk={(data) => handleUpdate(data)}
                    onCancel={() => setVisibleUpdate(false)}
                    width={800}
                >
                    <Container>
                        <h4 style={{ marginBottom: 20 }}>Thông tin</h4>
                        <Grid container spacing={10}>
                            <Grid item xs={6} >
                                <TextField
                                    required
                                    style={{ marginBottom: 20 }}
                                    id="name"
                                    size="small"
                                    defaultValue={roomUpdate.name}
                                    className="text-input"
                                    label="Tên phòng"
                                    variant="outlined"
                                    onChange={(e) => handleChangeUpdate(e)}
                                />
                                <TextField
                                    variant="outlined"
                                    id="roomType"
                                    // required
                                    size="small"
                                    value={roomUpdate.roomType}
                                    onChange={(e) => handleChangeUpdate(e)}
                                    style={{ marginBottom: 20 }}
                                    className="text-input"
                                    select
                                    label="Loại phòng"
                                // value={watch("categoryId")}
                                >
                                    {listCategory.map((category, index) => (
                                        <MenuItem key={index} value={listCategory[index]}>
                                            {listCategory[index]}
                                        </MenuItem>
                                    ))}
                                </TextField>

                                <h4 style={{ marginBottom: 20 }}>Thông số kỹ thuật</h4>
                                <TextField
                                    style={{ marginBottom: 20 }}
                                    id="length"
                                    defaultValue={roomUpdate.length}
                                    size="small"
                                    onChange={(e) => handleChangeUpdate(e)}
                                    type="number"
                                    InputProps={{ inputProps: { min: 0 } }}
                                    className="text-input"
                                    label="Chiều dài(m)"
                                    variant="outlined"
                                />
                                <TextField
                                    style={{ marginBottom: 20 }}
                                    id="width"
                                    size="small"
                                    defaultValue={roomUpdate.width}
                                    type="number"
                                    InputProps={{ inputProps: { min: 0 } }}
                                    className="text-input"
                                    label="Chiều rộng(m)"
                                    onChange={(e) => handleChangeUpdate(e)}
                                    endAdornment={<InputAdornment position="end">Kg</InputAdornment>}
                                    variant="outlined"
                                />

                                <TextField
                                    style={{ marginBottom: 20 }}
                                    id="height"
                                    size="small"
                                    type="number"
                                    onChange={(e) => handleChangeUpdate(e)}
                                    InputProps={{ inputProps: { min: 0 } }}
                                    className="text-input"
                                    defaultValue={roomUpdate.height}
                                    label="Chiều cao(m)"
                                    variant="outlined"
                                />

                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    className="text-input"
                                    id="description"
                                    label="Mô tả"
                                    defaultValue={roomUpdate.description}
                                    multiline
                                    rows={4}
                                    variant="outlined"
                                />
                            </Grid>
                        </Grid>
                    </Container>
                </Modal>
            </Container>
            <div style={{ paddingTop: 40 }}></div>
        </div >
    );
}

export default RoomDetail;