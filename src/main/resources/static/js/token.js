document.addEventListener('DOMContentLoaded', () => {
    function updateButtonsVisibility() {

        const tokenEl = document.getElementById('token');
        if (tokenEl && tokenEl.textContent && tokenEl.textContent !== "") {
            localStorage.setItem('token', tokenEl.textContent);
        }
        const roleEl = document.getElementById('role');
        if (roleEl && roleEl.textContent && roleEl.textContent !== "") {
            localStorage.setItem('role', roleEl.textContent);
        }

        let role = localStorage.getItem('role');
        const hasAccess = role === 'ADMIN' || role === 'USER';
        const hasFullAccess = role === 'ADMIN';


        document.querySelectorAll('#favorite').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#catalog').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#favorite-over').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#catalog-over').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#favorite-left').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#catalog-left').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#login').forEach(el => {
            el.hidden = hasAccess;
        });

        document.querySelectorAll('#logout').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#refresh').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        document.querySelectorAll('#refresh-over').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        document.querySelectorAll('#refresh-left').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        document.querySelectorAll('#add-new-admin').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        document.querySelectorAll('#add-new-admin-over').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        document.querySelectorAll('#add-new-admin-left').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        document.querySelectorAll('#added-functions').forEach(el => {
            el.hidden = !hasFullAccess;
        })

        let username = localStorage.getItem('username');
        document.querySelectorAll('#usernameString').forEach(el => {
            if (username && el.textContent !== username) {
                el.textContent = username;
            } else if (!username && el.textContent !== 'InvestMate') {
                el.textContent = 'InvestMate';
            }
        })

        const loginLink = document.getElementById('loginLink');
        const question = document.getElementById('question');
        if (role && loginLink && question && loginLink.style.display !== 'none') {
            loginLink.style.display = 'none';
            question.style.display = 'none';
        }

        const registerButton = document.getElementById('registerButton');
        if (role && registerButton && registerButton.textContent !== 'ЗАРЕГИСТРИРОВАТЬ') {
            registerButton.textContent = 'ЗАРЕГИСТРИРОВАТЬ';
        }

        document.querySelectorAll('[id^="favorite-uid"]').forEach(el=> {
                let favorite = getCookie(el.id);
                const favoriteText = el.querySelector('a');
                if (favorite && favoriteText && favorite === el.id.slice(12) &&
                    favoriteText.textContent !== "Убрать из избранного") {
                    favoriteText.textContent = "Убрать из избранного";
                } else if (favoriteText && favorite !== el.id.slice(12) &&
                    favoriteText.textContent !== "В избранное") {
                    favoriteText.textContent = "В избранное";
                }
            }
        )
    }

    function getCookie(name) {
        const cookies = document.cookie.split('; ');

        for (let cookie of cookies) {
            const [cookieName, cookieValue] = cookie.split('=');

            if (cookieName === name) {
                return decodeURIComponent(cookieValue);
            }
        }

        return null;
    }

    updateButtonsVisibility();

    const observer = new MutationObserver(function (mutations) {
        mutations.forEach(function (mutation) {
            if (mutation.type === 'childList') {
                // Проверяем, не появились ли новые элементы меню (клоны)
                updateButtonsVisibility();
            }
        });
    });

    // Начинаем наблюдение за body для отслеживания клонированных элементов
    observer.observe(document.body, {
        childList: true,
        subtree: true
    });

    // Также отслеживаем прокрутку (на случай sticky header)
    window.addEventListener('scroll', function () {
        const header = document.getElementById('xb-header-area');
        if (header && header.classList.contains('is-sticky')) {
            updateButtonsVisibility();
        }
    });

    // Отслеживаем изменения localStorage
    window.addEventListener('storage', function (e) {
        if (e.key === 'role') {
            updateButtonsVisibility();
        }
    });



});
// document.onclick = function(event) {
//     const link = event.target.closest('a');
//     console.log("onclick");
//
//     if (link && link.hasAttribute('href')) {
//         event.preventDefault();
//
//         const token = localStorage.getItem('token');
//         const originalHref = link.getAttribute('href');
//
//         if (token) {
//             const tempIframe = document.createElement('iframe');
//             tempIframe.style.display = 'none';
//             tempIframe.onload = function() {
//                 const xhr = new XMLHttpRequest();
//                 xhr.open('GET', originalHref);
//                 xhr.setRequestHeader('Authorization', `Bearer ${token}`);
//                 xhr.onload = function() {
//                     if (xhr.status === 200) {
//                         document.open();
//                         document.write(xhr.responseText);
//                         document.close();
//                     } else if (xhr.status === 401 || xhr.status === 403) {
//                         localStorage.removeItem('token');
//                         localStorage.removeItem('role');
//                         window.location.href = '/invest/sign-in';
//                     }
//                 };
//                 xhr.send();
//             };
//             console.log("append");
//             document.body.appendChild(tempIframe);
//             tempIframe.src = 'about:blank';
//         } else {
//             window.location.href = '/invest/sign-in';
//         }
//     }
// };
