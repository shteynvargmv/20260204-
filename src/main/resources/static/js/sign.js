async function register(element) {
    let username = document.querySelector('#username');
    let password = document.querySelector('#password');
    let email = document.querySelector('#email');
    const result = document.getElementById('result');
    console.log(username.value, password.value, email.value)

    let body = JSON.stringify({
        username: username.value,
        password: password.value,
        email: email.value,
        role: 0
    })
    try {
        const response = await fetch('/auth/register', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: body
        });

        if (response.ok) {
            const data = await response.json();
            console.log(data)
            if (data.created) {
                localStorage.removeItem('token');
                localStorage.removeItem('role');
                window.location.href = '/invest/sign-in';
            } else {
                localStorage.removeItem('token');
                localStorage.removeItem('role');
                result.textContent = `${data.registerResult}`;
            }
        }
    } catch (error) {
        result.textContent = 'Ошибка сети или сервера';
    }

}

async function login(element) {
    let username = document.querySelector('#username');
    let password = document.querySelector('#password');
    const result = document.getElementById('result');

    let body = JSON.stringify({
        username: username.value,
        password: password.value,
    })

    try {
        const response = await fetch('/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: body,
            credentials: 'include'
        });

        const data = await response.json();

        if (response.ok) {
            if (data.token) {
                localStorage.setItem('role', data.role);
                localStorage.setItem('token', data.token);
                window.location.href = '/invest/home';
            } else {
                result.textContent = data.loginResult;
            }
        } else {
            localStorage.removeItem('token');
            localStorage.removeItem('role');
            result.textContent = data.loginResult;
        }
    } catch (error) {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        result.textContent = 'Ошибка сети или сервера';
    }
}

// async function login(element) {
//     let username = document.querySelector('#username');
//     let password = document.querySelector('#password');
//     const result = document.getElementById('result');
//
//     let body = JSON.stringify({
//         username: username.value,
//         password: password.value,
//     })
//
//     try {
//         const response = await fetch('/auth/login', {
//             method: 'POST',
//             headers: {
//                 'Content-Type': 'application/json',
//             },
//             body: body
//         });
//         const data = await response.json();
//         if (response.ok) {
//             if (data.token && data.role) {
//                 localStorage.setItem('token', data.token);
//                 localStorage.setItem('role', data.role);
//                 // window.location.href = '/invest/home';
//                 const homeResponse = await fetch('/invest/home', {
//                     headers: {
//                         'Authorization': `Bearer ${data.token}`,
//                         'X-Custom-Header': 'value'
//                     }
//                 });
//                 if (homeResponse.ok) {
//                     const html = await homeResponse.text();
//                     document.open();
//                     document.write(html);
//                     document.close();
//                 }
//
//             } else if (data.loginResult) {
//                 localStorage.removeItem('token');
//                 localStorage.removeItem('role');
//                 result.textContent = `${data.loginResult}`;
//             } else {
//                 localStorage.removeItem('token');
//                 localStorage.removeItem('role');
//                 result.textContent = `Ошибка, попробуйте еще раз`;
//             }
//         }
//     } catch (error) {
//         localStorage.removeItem('token');
//         localStorage.removeItem('role');
//         result.textContent = 'Ошибка сети или сервера';
//     }
//
// }

async function logout(element) {

    let token = localStorage.getItem('token');
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    if (token) {
        const response = await fetch('/auth/logout', {
            method: 'POST',
            credentials: 'include'
        });
        if (response.ok) {
            window.location.href = '/invest/logout/success';
        }
    } else {
        localStorage.removeItem('token');
        localStorage.removeItem('role');
        window.location.href = '/invest/home';
    }

}