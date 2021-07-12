import React, {useState, useEffect} from 'react';
import TextField from '@material-ui/core/TextField';
import {makeStyles} from '@material-ui/core/styles'

import Button from '@material-ui/core/Button';
import Icon from '@material-ui/core/Icon';
import Grid from '@material-ui/core/Grid';
import MenuItem from '@material-ui/core/MenuItem';
import Select from '@material-ui/core/Select';
import InputLabel from '@material-ui/core/InputLabel';
import FormControl from '@material-ui/core/FormControl';
import Typography from '@material-ui/core/Typography';

import classes from './newTodo.module.css'

const NewTodo = (props) => {

    const [expireDate, setExprireDate] = useState("")
    const [content, setContent] = useState("")
    const [importance, setImportance] = useState("")
    const [filled, setFilled] = useState(false)

    useEffect(() => {
        if (expireDate && content && importance) {
            setFilled(true);
        }else {
            setFilled(false);
        }
    }, [expireDate, content, importance])

    const submitHandler = () => {
        const newTodo = {
            "expire": expireDate,
            "content": content,
            "importance": importance,
            "userName": "TARO",
            "status": 0
        }
        props.addTodo(newTodo)
    }

    return (
        <div className={classes.Body}>
            <Typography variant="h4">新規TODO</Typography>
            <Grid container className={classes.FormGrid} spacing={1}>
                <Grid item xs={12} className={classes.UpperContent}>
                    <TextField id="date" type="date" label="期限" InputLabelProps={{shrink: true}} onChange={(e) => setExprireDate(e.target.value)}/>
                    <FormControl className={classes.FormControl}>
                        <InputLabel className={classes.ImportanceLabel}>重要度</InputLabel>
                        <Select label="重要度" value={importance} onChange={(e) => setImportance(e.target.value)}>
                            <MenuItem value={1}>高</MenuItem>
                            <MenuItem value={2}>中</MenuItem>
                            <MenuItem value={3}>低</MenuItem>
                        </Select>
                    </FormControl>
                </Grid>
                <TextField className={classes.TodoLabel} id="standard-basic" label="TODO" onChange={(e) => setContent(e.target.value)}/>
                {filled ?
                <Button className={classes.SubmitButton} variant="contained" color="primary" onClick={() => submitHandler()}>送信</Button> :
                <Button className={classes.SubmitButton} variant="contained" color="primary" disabled>送信</Button>
                 }
            </Grid>
        </div>
      );

}

export default NewTodo;