import React, { useState } from 'react'
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardActionArea from '@material-ui/core/CardActionArea';
import CardActions from '@material-ui/core/CardActions';
import CardContent from '@material-ui/core/CardContent';
import CardMedia from '@material-ui/core/CardMedia';
import Button from '@material-ui/core/Button';
import Typography from '@material-ui/core/Typography';
import { Grid } from '@material-ui/core';
import { Link, useHistory } from 'react-router-dom';
import Axios from 'axios';

const useStyles = makeStyles({
    root: {
        maxWidth: 345,
    },
    media: {
        height: 140,
    },
});
const api = Axios.create({
    baseURL: 'http://localhost:8081/api/'
})
export default function Room(props) {
    const history = useHistory();
    const classes = useStyles();
    const { room } = props;
    const handleDelete = () => {
        console.log(room.id);
        // api.delete('/room/' + room.id);
    };
    return (
        <Grid item xs={3}>
            <Card className={classes.root}>
                <CardActionArea onClick={() => history.push("/room/" + room.id)}>
                    <CardMedia
                        className={classes.media}
                        image={room.url}
                        title="Contemplative Reptile"
                    />
                    <CardContent>
                        <Typography gutterBottom variant="h5" component="h2">
                            {room.name}
                        </Typography>
                        <Typography variant="body2" color="textSecondary" component="p">
                            {room.roomType}
                        </Typography>
                    </CardContent>
                </CardActionArea>
                <CardActions>
                    <Button size="small" color="primary">
                        Tìm hiểu thêm
                    </Button>
                    {/* <Button size="small" color="secondary" onClick={() => handleDelete()}>
                        Xóa
                    </Button> */}
                </CardActions>
            </Card>
        </Grid>
    );
}
