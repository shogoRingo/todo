import {useState, useEffect} from 'react';

import NewTodo from "./newTodo/newTodo"
import TodoList from "./todoList/todoList"

import Typography from '@material-ui/core/Typography';

import * as actions from '../../store/actions/index';

import {connect} from 'react-redux';

const Todo = props => {
    const [todo, setTodo] = useState([...props.todo])
    useEffect(() => {
        setTodo(props.todo)
    }, [props.todo])

    useEffect(() =>{
        props.fetchTodo()
    }, [])

    return (
        <div>
            <NewTodo addTodo={(todo)=>props.addTodo(todo)}/>
            <TodoList
                todo={todo}
                updateTodo={(id, status) => props.updateTodo(id, status)}
                deleteTodos={(ids) => props.deleteTodos(ids)}/>
        </div>
    )
}

const mapStateToProps = state => {
    return {
        todo: state.todo.todo
    }
}

const mapDispatchToProps = dispatch => {
    return {
        fetchTodo: () => dispatch(actions.fetchTodo()),
        addTodo: (todo) => dispatch(actions.addTodo(todo)),
        updateTodo: (id, status) => dispatch(actions.updateTodo(id, status)),
        deleteTodos: (ids) => dispatch(actions.deleteTodos(ids)),
    }
}

export default connect(mapStateToProps, mapDispatchToProps)(Todo);