async function favorite(element) {
    const uid = element.id.slice(12);
    const result = document.getElementById('refresh-result');
    const favoriteText = element.querySelector('a'); //document.getElementById('favorite-text');
    const toastLiveExample = document.getElementById('liveToast');
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample);

    if (favoriteText.textContent === 'В избранное') {

        try {
            const response = await fetch(`/invest/favorite-add/${uid}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include'
            });

            const data = await response.json();

            if (response.ok) {
                if (data.favoriteUids.includes(uid)) {
                    favoriteText.textContent = "Убрать из избранного";
                }
            } else {
                result.textContent = 'Ошибка: ' + data.favoriteResult;
                toastBootstrap.show()
            }
        } catch (error) {
            result.textContent = 'Ошибка сети или сервера';
            toastBootstrap.show()
        }
    } else if (favoriteText.textContent === 'Убрать из избранного') {

        try {
            const response = await fetch(`/invest/favorite-del/${uid}`, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                credentials: 'include'
            });

            const data = await response.json();

            if (response.ok) {
                if (!data.favoriteUids.includes(uid)) {
                    favoriteText.textContent = "В избранное";
                }
            } else {
                result.textContent = 'Ошибка: ' + data.favoriteResult;
                toastBootstrap.show()
            }
        } catch (error) {
            result.textContent = 'Ошибка сети или сервера';
            toastBootstrap.show()
        }
    }

}

async function detailed(element) {
    const uid = element.id;
    let el = document.getElementById('detailed-' + uid);
    if (el.style.visibility === 'visible') {
        el.style.visibility = 'collapse';
    } else if (el.style.visibility === 'collapse') {
        el.style.visibility = 'visible';
    }

    if (element.classList.contains('fa-plus')) {
        element.classList.remove('fa-plus');
        element.classList.add('fa-minus');
    } else {
        element.classList.remove('fa-minus');
        element.classList.add('fa-plus');
    }
}