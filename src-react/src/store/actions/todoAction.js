import * as actionType from './actionTypes';
import axios from "../../axios"

const fetchTodoSuccess = (todos) => {
    return {
        type: actionType.FETCH_TODO,
        todos: todos
    }
}

/**
 * TODOを追加する
 * @param {*} todo 
 * @returns 
 */
export const addTodo = (todo) => {
    return dispatch => {
        // 新しいTODOをポストする
        axios.post("/todo", todo, {
            headers: {
                'Content-Type': "application/json;charset=utf-8"
            }
        })
        .then(res => {

            // TODOを取得する
            axios.get("/todo")
            .then(res => {
                dispatch(fetchTodoSuccess(res.data))
            })
            .catch(err => {
                console.log(err);
            })
        })
        .catch(err => {
            console.log(err)
        })
    }
}

/**
 * TODOを更新する
 * @param {*} id 
 * @param {*} status 
 * @returns 
 */
export const updateTodo = (id, status) => {
    return dispatch => {
        axios.put("/todo/" + id, {status: status}, {
            headers: {
                'Content-Type': "application/json;charset=utf-8"
            }
        })
        .then(res => {

            // TODOを取得する
            axios.get("/todo")
            .then(res => {
                dispatch(fetchTodoSuccess(res.data))
            })
            .catch(err => {
                console.log(err);
            })
        })
        .catch(err => {
            console.log(err);
        })
    }
}

/**
 * TODOを複数件削除する
 * @param {*} ids 
 * @returns 
 */
export const deleteTodos = (ids) => {
    return dispatch => {
        axios.delete("/todo", {data: {idList: ids}}, {
            headers: {
                'Content-Type': 'application/json;charset=utf-8',
            }
        })
        .then(red => {

            // TODOを取得する
            axios.get("/todo")
            .then(res => {
                dispatch(fetchTodoSuccess(res.data))
            })
            .catch(err => {
                console.log(err);
            })
        })
        .catch(err => {
            console.log(err);
        })
    }
}

export const fetchTodo = () => {
    return dispatch => {
        axios.get("/todo")
        .then(res => {
            dispatch(fetchTodoSuccess(res.data))
        })
        .catch(err => {
            console.log(err);
        })
    }
}
