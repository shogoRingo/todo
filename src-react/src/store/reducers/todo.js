import * as actionType from '../actions/actionTypes';

const initialState = {
    todo: [
        {id: 1, expire: "2021/12/12", importance: "高", content:"sara arai", state: "未完了", "delete": ""},
    
    ]
}

/**
 * リストを取得する
 * @param {*} state 
 * @param {*} action 
 * @returns 
 */
const fetchTodo = (state, action) => {
    return {todo: action.todos}
}

/**
 * リストにTODOを追加する
 * @param {}} state 
 * @param {*} action 
 * @returns 
 */
const addTodo = (state, action) => {
    return {
        todo: state.todo.concat([action.todo])
    }
}

/**
 * TODOを更新する
 * @param {*}} state 
 * @param {*} action 
 */
const updateTodo = (state, action) => {
    const filtered = state.todo.filter(e => e.id != action.todo.id)
    filtered.push(action.todo)
    return {todo: filtered}
}

/**
 * TODOを削除する
 * @param {*} state 
 * @param {*} action 
 * @returns 
 */
const deleteTodo = (state, action) => {
    const filtered = state.todo.filter(e => e.id != action.id)
    return {todo: filtered}
}

/**
 * reducer
 * @param {*} state 
 * @param {*} aciton 
 */
const reducer = (state=initialState, action) => {
    switch(action.type) {
        case actionType.FETCH_TODO: return fetchTodo(state, action)
        case actionType.ADD_TODO: return addTodo(state, action)
        case actionType.UPDATE_TODO: return updateTodo(state, action)
        case actionType.DELETE_TODO: return deleteTodo(state, action)
        default: return state;
    }
}

export default reducer;