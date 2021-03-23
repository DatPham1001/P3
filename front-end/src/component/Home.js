import { Container, Grid, InputAdornment, makeStyles, MenuItem, Paper, Snackbar, TextField } from '@material-ui/core'
import { grey } from '@material-ui/core/colors';
import { Button } from 'antd';
import Modal from 'antd/lib/modal/Modal';
import Axios from 'axios';
import React, { useEffect, useState } from 'react'
import Alert from '@material-ui/lab/Alert';
import { Controller, useForm } from 'react-hook-form';
import { useHistory } from 'react-router-dom';
import Header from './Header';
import Room from './Room/Room';
import "./style.css"
const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
        backgroundColor: grey,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
}));
const api = Axios.create({
    baseURL: 'http://localhost:8081/api/'
})
export default function Home() {
    const classes = useStyles();
    const history = useHistory();
    const [linkImg, setlinkImg] = useState("https://firebasestorage.googleapis.com/v0/b/img-base-d6dac.appspot.com/o/images%2FNo-image-news.png?alt=media&token=3fa591a6-fef7-4f39-a6fd-c18ad4e2f029");
    const [listCategory, setlistCategory] = useState(["Laboratory", "Office", "Accommodation"])
    const [roomCreate, setroomCreate] = useState({ name: "", roomType: "", length: "", width: "", height: "", description: "", url: "" })
    const [alertCreated, setalertCreated] = useState(false);
    const [alertCreatedMessage, setalertCreatedMessage] = useState({ message: 'Tạo phòng thành công!', status: 'success' });
    const handleSubmit = () => {
        console.log(roomCreate);
        api.post('/room', roomCreate)
            .then((res) => {
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
    const [visible, setVisible] = useState(false);
    const [room, setRoom] = useState([]);
    
    const handleChange = (event) => {
        setroomCreate((prevState) => ({
            ...prevState,
            [event.target.id]: event.target.value
        }));
    }
    useEffect(() => {
        api.get('/room').then(res => {
            console.log(res);
            setRoom(res.data);
        })
    }, []);
    return (
        <div
        // style={{ backgroundImage: `url("https://pubnative.net/wp-content/uploads/2019/08/PubNative-Web-Background-Design.png")` }}
        >
            <Header>
            </Header>
            <div className={classes.root}>
                <Container>
                    <Snackbar open={alertCreated} autoHideDuration={1000}
                        anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
                        onClose={() => setalertCreated(false)}
                    >
                        <Alert variant="filled" severity={alertCreatedMessage.status}>
                            {alertCreatedMessage.message}
                        </Alert>
                    </Snackbar>
                    <div className="add-btn">
                        <Button style={{ marginBottom: 50 }} type="primary" onClick={() => setVisible(true)}>Tạo phòng</Button>
                    </div>
                    <Grid container spacing={3}>
                        {room.length > 0 && room.map((r, i) =>
                            <Room room={r} />
                        )}
                    </Grid>
                </Container>
                
                <Modal
                    title="Tạo phòng"
                    centered
                    visible={visible}
                    onOk={(data) => handleSubmit(data)}
                    onCancel={() => setVisible(false)}
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
                                    className="text-input"
                                    label="Tên phòng"
                                    variant="outlined"
                                    onChange={(e) => handleChange(e)}
                                />
                                <TextField
                                    variant="outlined"
                                    id="roomType"
                                    // required
                                    size="small"
                                    onChange={(e) => setroomCreate({ ...roomCreate, roomType: e.target.value })}
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
                                    size="small"
                                    onChange={(e) => handleChange(e)}
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
                                    type="number"
                                    InputProps={{ inputProps: { min: 0 } }}
                                    className="text-input"
                                    label="Chiều rộng(m)"
                                    onChange={(e) => handleChange(e)}
                                    endAdornment={<InputAdornment position="end">Kg</InputAdornment>}
                                    variant="outlined"
                                />

                                <TextField
                                    style={{ marginBottom: 20 }}
                                    id="height"
                                    size="small"
                                    type="number"
                                    onChange={(e) => handleChange(e)}
                                    InputProps={{ inputProps: { min: 0 } }}
                                    className="text-input"
                                    label="Chiều cao(m)"
                                    variant="outlined"
                                />

                            </Grid>
                            <Grid item xs={6}>
                                <TextField
                                    className="text-input"
                                    id="description"
                                    label="Mô tả"
                                    multiline
                                    rows={4}
                                    variant="outlined"
                                />
                                {/* <Paper variant="outlined" className="upload-img-container" >
                            <div className="img-create-container">
                                <img className="image-responsive" src={linkImg}></img>
                            </div>
                            <div style={{ textAlign: "center" }}>
                                <Button
                                    className="upload-btn"
                                    variant="contained"
                                    color="primary"
                                    size="large"
                                    component="label"
                                >
                                    Upload
                                    <input
                                        type="file"
                                        accept="image/*"
                                        style={{ display: "none" }}
                                    // onChange={handleUpload2}
                                    />
                                </Button>
                            </div>
                        </Paper> */}
                            </Grid>
                        </Grid>
                    </Container>
                </Modal>
            </div>
        </div >
    )
}
