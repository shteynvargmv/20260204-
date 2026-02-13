window.addEventListener('beforeunload', function() {
    const scrollData = {
        path: window.location.pathname,
        position: window.scrollY,
        timestamp: Date.now()
    };
    sessionStorage.setItem('lastScrollPosition', JSON.stringify(scrollData));
});


document.addEventListener('DOMContentLoaded', function() {
    const saved = sessionStorage.getItem('lastScrollPosition');
    if (saved) {
        const scrollData = JSON.parse(saved);
        if (scrollData.path === window.location.pathname) {
        setTimeout(() => {
            window.scrollTo(0, scrollData.position);
        });
        }
    }
});