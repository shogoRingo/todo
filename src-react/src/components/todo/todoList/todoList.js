import React, {useState} from 'react';
import {DataGrid} from '@material-ui/data-grid';
import Typography from '@material-ui/core/Typography';
import todo from '../todo';
import Button from '@material-ui/core/Button';
import { deleteTodo } from '../../../store/actions';

import classes from './todoList.module.css';

const columns = [
    {field: 'expire', headerName: '期限', width: 110},
    {field: 'importance', headerName: '重要度', width: 120},
    {field: 'content', headerName: '内容', width: 350},
    {field: 'status', headerName: '状態', width: 110}
]

const DONE_LABEL = "完了";
const UNDONE_LABEL = "未完了";
const DONE_VAL = 1;
const UNDONE_VAL = 0;

const TodoList = (props) => {

    const [selected, setSelected] = useState([])

    const rows = props.todo.map((todo) => {
        return {...todo, status:todo.status==UNDONE_VAL ? UNDONE_LABEL: DONE_LABEL, importance: todo.importance==1?"高": todo.importance==2? "中": "低"}
    })

    const update = (params) => {
        switch (params.field) {
            case "status": props.updateTodo(params.id, params.value==UNDONE_LABEL? DONE_VAL: UNDONE_VAL)
            default: return
        }
    }

    const deleteTodos = () => {
        if(selected) {
            props.deleteTodos(selected)
        }
    }

    return (
        <div>
            <Typography variant="h4">TODOリスト</Typography>
            <div style={{ height: 400, width: '100%' }}>
                <DataGrid
                    rows={rows}
                    columns={columns}
                    pageSize={5}
                    onCellClick={(params, event) => update(params)}
                    checkboxSelection
                    disableSelectionOnClick 
                    onSelectionModelChange={(selected) => setSelected([...selected.selectionModel.map(it => it)])}
                    />
            </div>
            <Button className={classes.DeleteButton} variant="contained" color="primary" onClick={deleteTodos}>削除</Button>
        </div>
      );

}

export default TodoList;