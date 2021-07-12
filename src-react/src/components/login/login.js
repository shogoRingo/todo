import React, {useState} from 'react';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import TextField from '@material-ui/core/TextField';
import FormControlLabel from '@material-ui/core/FormControlLabel';
import Checkbox from '@material-ui/core/Checkbox';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import Typography from '@material-ui/core/Typography';
import Container from '@material-ui/core/Container';

import * as classes from './login.module.css'

const EMAIL_VALIDATE_EXP = new RegExp("[a-zA-Z0-9._-]+@[a-z-A-Z0-9._-]+\\.+[a-z]+")

const SignIn = () => {
    const [email, setEmail] = useState("")
    const [password, setPassword] = useState("")
    const [isRemembered, setIsRemembered] = useState(false)
    const [touchedList, setTouchedList] = useState({email: false, password: false})
    const [isAllValidated, setIsAllValidated] = useState(false)

    const onEmailChange = (value) => {
        setTouchedList({...touchedList, email: true})
        setIsAllValidated(EMAIL_VALIDATE_EXP.test(value))
        setEmail(value)
    }
    const onPasswordChange = (value) => {
        setTouchedList({...touchedList, password: true})
        setPassword(value)
    }

    // ボタンの活性/非活性
    var fullfilled = isAllValidated
    for (const [_, value] of touchedList.entries()) {
        if(!value) fullfilled = false
    }

    // メールアドレスのバリデーションチェック
    const isEmailError = !isAllValidated

    return(
        <Container component="main" maxWidth="xs">
            <div className={classes.Paper}>
                <Avatar className={classes.Avator}>
                    <LockOutlinedIcon />
                </Avatar>
                <Typography component="h1" variant="h5">
                    ログイン
                </Typography>
                <form className={classes.Form} noValidate>
                    <TextField
                        variant="outlined"
                        margin="normal"
                        required
                        fullWidth
                        id="email"
                        label="Email Address"
                        name="email"
                        autoComplete="email"
                        autoFocus
                        onChange={(e) => onEmailChange(e.target.value)}
                    />
                    <TextField
                      variant="outlined"
                      margin="normal"
                      required
                      fullWidth
                      name="password"
                      label="Password"
                      type="password"
                      id="password"
                      error={isEmailError}
                      autoComplete="current-password"
                      onChange={(e) => onPasswordChange(e.target.value)}
                    />
                    <FormControlLabel
                      control={<Checkbox value="remember" color="primary" />}
                      label="IDを記憶する"
                    />
                    <Button
                      type="submit"
                      fullWidth
                      variant="contained"
                      color="primary"
                      className={classes.Submit}
                      disabled={!fullfilled}
                    >
                      ログインa
                    </Button>
                    <Grid container>
                        <Grid item xs>
                        <Link href="#" variant="body2">
                            パスワードを忘れた場合
                        </Link>
                        </Grid>
                        <Grid item>
                        <Link href="#" variant="body2">
                            {"新規登録する場合"}
                        </Link>
                        </Grid>
                    </Grid>
                </form>
            </div>
        </Container>
    )
}

export default SignIn