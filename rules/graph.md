# graphify - Knowledge Graph Builder

Turn any folder of files (code, docs, papers, images, video) into a persistent, queryable knowledge graph with community detection, god nodes, and three outputs: interactive HTML, GraphRAG-ready JSON, and a plain-language GRAPH_REPORT.md.

**Version:** 0.9.4
**Skill location:** `~/.claude/skills/graphify/`

## Required Files

All paths below are relative to `~/.claude/skills/graphify/`. Load the appropriate file(s) based on the task.

| File | When to Load |
|------|-------------|
| `SKILL.md` | **Always.** Full pipeline: detect -> extract (AST + semantic) -> build -> cluster -> label -> export |
| `.graphify_version` | Version info (v0.9.4) |
| `references/extraction-spec.md` | Step 3 Part B - when corpus has docs/papers/images (subagent prompt with JSON schema, node-ID rules, confidence rubric) |
| `references/query.md` | `/graphify query`, `path`, `explain` - existing graph queries with vocab expansion, BFS/DFS traversal, inline NetworkX fallback |
| `references/update.md` | `--update` or `--cluster-only` flags |
| `references/transcribe.md` | When video/audio files detected in corpus |
| `references/github-and-merge.md` | GitHub URLs or multi-path merge |
| `references/add-watch.md` | `--watch` flag or `/graphify add <url>` |
| `references/exports.md` | Export flags: `--wiki`, `--neo4j`/`--neo4j-push`, `--falkordb`/`--falkordb-push`, `--svg`, `--graphml`, `--mcp` |
| `references/hooks.md` | Post-commit hook install or CLAUDE.md integration |

## Quick Reference

- **Invoke:** `/graphify <path>` (defaults to `.` if no path given)
- **Flags:** `--mode deep`, `--update`, `--directed`, `--no-viz`, `--obsidian`, `--wiki`, `--neo4j`, `--svg`, `--graphml`, `--mcp`, `--watch`
- **Subcommands:** `query`, `path`, `explain`, `add`, `hook install`
- **Install:** `uv tool install graphifyy` or `pip install graphifyy`
- **No API key needed** - code extracted via AST (free). Semantic extraction uses Gemini only if `GEMINI_API_KEY` is set; otherwise the host agent handles it.
- **Outputs:** `graphify-out/graph.html`, `graphify-out/GRAPH_REPORT.md`, `graphify-out/graph.json`
