import React, { useEffect } from 'react';

import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { CardActionArea, CardMedia, Grid } from '@material-ui/core';
import Axios from 'axios';
const api = Axios.create({
    baseURL: 'http://localhost:8081/api/'
})
const useStyles = makeStyles({
    root: {
        maxWidth: 400,
    },
    media: {
        height: 120,
    },
});

function Device(props) {
    const classes = useStyles();
    const { device } = props;
    useEffect(() => {
        console.log(props);
    }, [])
    const handleDelete = () => {
        console.log(device.id)
        // api.delete('/device/' + device.id).then(res => {

        // })
    }
    return (
        <Grid xs={4}>
            <Card className={classes.root}>
                <CardActionArea>
                    <CardMedia
                        className={classes.media}
                        image={'https://upload.wikimedia.org/wikipedia/commons/3/38/Arduino_Uno_-_R3.jpg'}
                        title="Contemplative Reptile"
                    />
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="h2">
                            Tên thiết bị: {device.name}
                        </Typography>
                        <Typography variant="body2" color="textSecondary" component="p">
                            {device.deviceType}
                        </Typography>
                    </CardContent>
                </CardActionArea>
                <CardActions>
                    <Button size="small" color="secondary" onClick={() => handleDelete()}>
                        Xóa
                    </Button>
                </CardActions>
            </Card>
        </Grid>
    );
}

export default Device;