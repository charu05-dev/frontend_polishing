(function () {
    var toggle = document.querySelector(".menu-toggle");
    var links = document.querySelector(".nav-links");

    if (toggle && links) {
        toggle.addEventListener("click", function () {
            links.classList.toggle("open");
            toggle.setAttribute(
                "aria-expanded",
                links.classList.contains("open") ? "true" : "false"
            );
        });
    }

    // Close mobile menu when a link is tapped
    if (links) {
        links.querySelectorAll("a").forEach(function (link) {
            link.addEventListener("click", function () {
                links.classList.remove("open");
                if (toggle) {
                    toggle.setAttribute("aria-expanded", "false");
                }
            });
        });
    }

    // Auto-submit filter form on dropdown change
    var filterForm = document.querySelector(".filters-panel");
    if (filterForm) {
        filterForm.querySelectorAll("select").forEach(function (sel) {
            sel.addEventListener("change", function () {
                filterForm.submit();
            });
        });
    }

    // Smooth fade-in for product cards on scroll
    if ("IntersectionObserver" in window) {
        var observer = new IntersectionObserver(
            function (entries) {
                entries.forEach(function (entry) {
                    if (entry.isIntersecting) {
                        entry.target.style.opacity = "1";
                        entry.target.style.transform = "translateY(0)";
                        observer.unobserve(entry.target);
                    }
                });
            },
            { threshold: 0.1 }
        );

        document.querySelectorAll(".product-card, .order-card, .why-card").forEach(function (card) {
            card.style.opacity = "0";
            card.style.transform = "translateY(20px)";
            card.style.transition = "opacity 0.5s ease, transform 0.5s ease";
            observer.observe(card);
        });
    }
})();
