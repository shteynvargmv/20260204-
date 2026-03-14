async function setFilter(element) {
    let input = document.querySelector('#globalSearch');
    let searchValue = input.value.toLowerCase();

    let checkedCheckboxes = document.querySelectorAll('[id^="sector_"]:checked');
    let selectedSectors = Array.from(checkedCheckboxes).map(checkbox => {
        return checkbox.value;
    });
    const type = element.dataset.type;
    let body = "";
    let checkedShareCheckboxes = document.querySelectorAll('[id^="parameter_share_"]:checked');
    let selectedShareParameters = Array.from(checkedShareCheckboxes).map(checkbox => {
        return checkbox.value;
    });
    let checkedBondCheckboxes = document.querySelectorAll('[id^="parameter_bond_"]:checked');
    let selectedBondParameters = Array.from(checkedBondCheckboxes).map(checkbox => {
        return checkbox.value;
    });
    let checkedAllCheckboxes = document.querySelectorAll('[id^="parameter_all_"]:checked');
    let selectedAllParameters = Array.from(checkedAllCheckboxes).map(checkbox => {
        return checkbox.value;
    });
    if (type === 'share' || type === 'all') {
        body = JSON.stringify({
            filter: { selectedSectors: selectedSectors,
                      selectedShareParameters: selectedShareParameters,
                      selectedBondParameters: null,
                      selectedAllParameters: selectedAllParameters,
                      searchValue: searchValue }
        })
    }
    if (type === 'bond' || type === 'all') {
        body = JSON.stringify({
            filter: { selectedSectors: selectedSectors,
                selectedShareParameters: null,
                selectedBondParameters: selectedBondParameters,
                selectedAllParameters: selectedAllParameters,
                searchValue: searchValue }
        })
    }
    if (type === 'all') {
        body = JSON.stringify({
            filter: { selectedSectors: selectedSectors,
                selectedShareParameters: selectedShareParameters,
                selectedBondParameters: selectedBondParameters,
                selectedAllParameters: selectedAllParameters,
                searchValue: searchValue }
        })
    }

    if (type === 'currency') {
        body = JSON.stringify({
            filter: { selectedSectors: null,
                selectedShareParameters: null,
                selectedBondParameters: null,
                selectedAllParameters: null,
                searchValue: searchValue }
        })
    }

    fetch('/invest/catalog/filter', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: body
    })
        .then(response => {
            if (response.ok) {
                window.location.href = '/invest/catalog/' + type;
            }
        })
}
