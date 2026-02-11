document.addEventListener('DOMContentLoaded', () => {
    // Функция для установки видимости кнопок
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

        // Обновляем видимость для всех элементов (включая клонированные)
        document.querySelectorAll('#favorite').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#catalog').forEach(el => {
            el.hidden = !hasAccess;
        });

        // Обновляем видимость для всех элементов (включая клонированные)
        document.querySelectorAll('#favorite-over').forEach(el => {
            el.hidden = !hasAccess;
        });

        document.querySelectorAll('#catalog-over').forEach(el => {
            el.hidden = !hasAccess;
        });

        // Обновляем видимость для всех элементов (включая клонированные)
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
    }

    // Первоначальная установка видимости
    updateButtonsVisibility();

    // Отслеживаем появление sticky-меню
    const observer = new MutationObserver(function(mutations) {
        mutations.forEach(function(mutation) {
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
    window.addEventListener('scroll', function() {
        const header = document.getElementById('xb-header-area');
        if (header && header.classList.contains('is-sticky')) {
            // При sticky состоянии обновляем видимость
            updateButtonsVisibility();
        }
    });

    // Отслеживаем изменения localStorage
    window.addEventListener('storage', function(e) {
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
