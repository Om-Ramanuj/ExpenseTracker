"use strict";
function animateTotal(display, total) {
    let current = 0;
    const step = total / 60;
    const update = () => {
        if (current < total) {
            current += step;
            display.textContent = Math.floor(current).toLocaleString();
            requestAnimationFrame(update);
        }
        else {
            display.textContent = total.toFixed(2).toLocaleString();
        }
    };
    update();
}
function downloadChart(chartId, filename) {
    const canvas = document.getElementById(chartId);
    if (!canvas)
        return;
    const link = document.createElement('a');
    link.download = `${filename}.png`;
    link.href = canvas.toDataURL();
    link.click();
}
function initCharts() {
    // @ts-ignore: Thymeleaf replaces at runtime
    const categoryData = [[$, { categorySummary }]];
    // @ts-ignore
    const months = [[$, { months }]];
    // @ts-ignore
    const amounts = [[$, { amounts }]];
    const categoryLabels = Object.keys(categoryData);
    const categoryValues = Object.values(categoryData);
    const pieColors = [
        "#3C6E71", "#284B63", "#D9D9D9", "#C6AC8F", "#B2B1B9", "#7D98A1"
    ];
    new Chart(document.getElementById("pieChart"), {
        type: 'doughnut',
        data: {
            labels: categoryLabels,
            datasets: [{
                    data: categoryValues,
                    backgroundColor: pieColors,
                    borderWidth: 2,
                    hoverOffset: 8
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { position: 'bottom' },
                tooltip: {
                    callbacks: {
                        label: (ctx) => `${ctx.label}: ₹${ctx.raw.toLocaleString()}`
                    }
                }
            },
            animation: {
                animateRotate: true,
                duration: 800
            }
        }
    });
    new Chart(document.getElementById("barChart"), {
        type: 'bar',
        data: {
            labels: months,
            datasets: [{
                    label: 'Monthly Spending (₹)',
                    data: amounts,
                    backgroundColor: "#3C6E71",
                    borderRadius: 6,
                    barThickness: 30
                }]
        },
        options: {
            responsive: true,
            plugins: {
                legend: { display: false },
                tooltip: {
                    callbacks: {
                        label: (ctx) => `₹${ctx.raw.toLocaleString()}`
                    }
                }
            },
            scales: {
                y: {
                    beginAtZero: true,
                    ticks: {
                        callback: (value) => `₹${value}`
                    },
                    grid: {
                        color: 'rgba(0,0,0,0.05)'
                    }
                },
                x: {
                    grid: { display: false }
                }
            },
            animation: {
                duration: 1000,
                easing: 'easeOutBounce'
            }
        }
    });
    const display = document.getElementById('totalExpense');
    const total = categoryValues.reduce((a, b) => a + b, 0);
    animateTotal(display, total);
}
document.addEventListener("DOMContentLoaded", () => {
    initCharts();
});
