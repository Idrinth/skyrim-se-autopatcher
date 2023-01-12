# Skyrim SE Autopatcher

This project is intended to replace xEdit in a specific usecase: Semi-Automatically patching mods. Think of it as the next generation of my auto patcher helper script.

It is not intended as a short term project, so you'll only slowly see progress.

## Intended benefits

- Threading: xEdit does not allow threading, since it's core is single threaded. For this specific use case, threading should increase the performance noticeably.
- Object Lists: xEdit can't handle lists of objects in a way, that allows for reasonable comparisons across leveled lists. Reimplementing this should bring the quality up to Mator Smash and similar solutions.
- Error Handling: xEdit does allow for capturing errors, but leaves very little information to react on. This seems to be by design and leaves some mods unpatchable do errors that could be ignored. Rewriting this should make the patcher handle invalid elements like Skyrim - ingnoring them where possible.
