.PHONY: bump-version publish publish-local

first-version:
	@deno run --allow-run --allow-write --allow-read scripts/bump-version.js --first-version

bump-version:
	@deno run --allow-run --allow-write --allow-read scripts/bump-version.js
