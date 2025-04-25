document.addEventListener('DOMContentLoaded', function() {
    const searchForm = document.getElementById('searchForm');
    const searchInput = document.getElementById('searchInput');

    // Add event listener for form submission (optional for AJAX implementation)
    // This is commented out as we're using Thymeleaf form submission
    /*
    searchForm.addEventListener('submit', function(e) {
        e.preventDefault();

        const query = searchInput.value.trim();
        const language = document.getElementById('languageSelect').value;

        if (query) {
            fetchResults(query, language);
        }
    });
    */

    // Function to fetch results via AJAX (if you want to implement dynamic loading)
    function fetchResults(query, language) {
        const url = `/api/search?query=${encodeURIComponent(query)}${language ? `&language=${encodeURIComponent(language)}` : ''}`;

        fetch(url)
            .then(response => response.json())
            .then(data => {
                displayResults(data);
            })
            .catch(error => {
                console.error('Error fetching results:', error);
            });
    }

    // Function to display results dynamically (for AJAX implementation)
    function displayResults(results) {
        const resultsContainer = document.querySelector('.results-container');
        if (!resultsContainer) return;

        resultsContainer.innerHTML = '';

        if (results.length === 0) {
            resultsContainer.innerHTML = '<p>No results found. Try a different search query.</p>';
            return;
        }

        results.forEach(item => {
            const resultItem = document.createElement('div');
            resultItem.className = 'result-item';

            resultItem.innerHTML = `
                <div class="result-header">
                    <h3>
                        <a href="${item.repository.htmlUrl}" target="_blank">${item.repository.fullName}</a> / 
                        <a href="${item.htmlUrl}" target="_blank">${item.path}</a>
                    </h3>
                </div>
                <div class="result-body">
                    <pre class="code-snippet">${item.codeSnippet || 'No preview available'}</pre>
                </div>
                <div class="result-footer">
                    <a href="${item.htmlUrl}" target="_blank" class="view-btn">View on GitHub</a>
                </div>
            `;

            resultsContainer.appendChild(resultItem);
        });
    }

    // Highlight search term in results
    function highlightSearchTerm() {
        const urlParams = new URLSearchParams(window.location.search);
        const searchTerm = urlParams.get('query');

        if (searchTerm && document.querySelectorAll('.code-snippet').length > 0) {
            document.querySelectorAll('.code-snippet').forEach(snippet => {
                const content = snippet.textContent;
                if (content.includes(searchTerm)) {
                    const regex = new RegExp(searchTerm, 'gi');
                    snippet.innerHTML = content.replace(
                        regex,
                        match => `<mark>${match}</mark>`
                    );
                }
            });
        }
    }

    // Execute highlighting if we're on the results page
    if (window.location.pathname.includes('search')) {
        highlightSearchTerm();
    }
});