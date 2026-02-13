document.getElementById('xb-loadding').style.display = 'none';document.addEventListener('DOMContentLoaded', function() {
    console.log('Menu script loaded');

    // Элементы меню
    const menuToggle = document.querySelector('.xb-menu-toggle');
    const menuClose = document.querySelector('.xb-menu-close');
    const menuBackdrop = document.querySelector('.xb-header-menu-backdrop');
    const headerMenu = document.querySelector('.xb-header-menu');

    // Проверка элементов
    console.log('Menu toggle:', menuToggle);
    console.log('Menu:', headerMenu);

    // Открытие меню
    if (menuToggle && headerMenu) {
        menuToggle.addEventListener('click', function(e) {
            e.preventDefault();
            console.log('Opening menu');
            headerMenu.classList.add('active');
            document.body.style.overflow = 'hidden';
        });
    }

    // Закрытие меню
    if (menuClose && headerMenu) {
        menuClose.addEventListener('click', function(e) {
            e.preventDefault();
            console.log('Closing menu');
            headerMenu.classList.remove('active');
            document.body.style.overflow = '';
        });
    }

    // Закрытие по клику на backdrop
    if (menuBackdrop && headerMenu) {
        menuBackdrop.addEventListener('click', function() {
            console.log('Closing via backdrop');
            headerMenu.classList.remove('active');
            document.body.style.overflow = '';
        });
    }

    // Закрытие по клавише ESC
    document.addEventListener('keydown', function(e) {
        if (e.key === 'Escape' && headerMenu && headerMenu.classList.contains('active')) {
            headerMenu.classList.remove('active');
            document.body.style.overflow = '';
        }
    });
});