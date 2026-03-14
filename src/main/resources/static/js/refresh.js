async function refresh(element){
    const uid = document.getElementById('uid').textContent;
    const result = document.getElementById('refresh-result');
    const toastLiveExample = document.getElementById('liveToast');
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample);
    document.getElementById('global-loader').style.display = 'flex';

    try {
        const response = await fetch(`/invest/refresh/${uid}`, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include'
        });

        const data = await response.json();

        if (response.ok) {
            document.getElementById('update-date').textContent = data.updateDateLocalString;
            document.getElementById('ticker').textContent = data.instrumentDto.ticker;
            document.getElementById('price').textContent = `Цена: ${data.priceString}`;
            document.getElementById('lot').textContent = `Лотность: ${data.instrumentDto.lot} шт`;
            // result.textContent = 'Обновление выполнено успешно';
            // toastBootstrap.show()
        } else {
            result.textContent = 'Ошибка обновления ' + data.refreshResult;
            toastBootstrap.show()
        }
    } catch (error) {
        result.textContent = 'Ошибка сети или сервера';
        toastBootstrap.show()
    } finally {
        document.getElementById('global-loader').style.display = 'none';
    }
}

async function refreshAll(element) {
    const result = document.getElementById('refresh-result');
    const toastLiveExample = document.getElementById('liveToast');
    const toastBootstrap = bootstrap.Toast.getOrCreateInstance(toastLiveExample);


    try {
        result.textContent = 'Обновление запущено';
        toastBootstrap.show();

        const response = await fetch('/invest/refresh/all', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            credentials: 'include'
        });

        const data = await response.text();

        if (response.ok) {
            result.textContent = 'Обновление выполнено успешно';
            toastBootstrap.show()

        } else {
            result.textContent = 'Ошибка обновления ' + data.refreshResult;
            toastBootstrap.show()
        }
    } catch (error) {
        result.textContent = 'Ошибка сети или сервера';
        toastBootstrap.show()
    }
}