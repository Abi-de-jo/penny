# Product

<!-- impeccable:product-schema 1 -->

## Platform

android

## Users

Penny is for an individual Android user who wants a low-effort view of everyday money movement without manually entering every transaction.

## Product Purpose

Penny captures new bank transaction SMS, records money in and money out, and asks the user to add a short human title to spending. Success means a transaction is captured reliably and can be understood later at a glance.

## Positioning

Penny is a local-first SMS ledger: it turns bank alerts already delivered to the device into a private, readable spending history without requesting bank credentials.

## Operating Context

The app is used on an Android phone after UPI, card, and bank transactions. It must remain useful when opened briefly, when a debit arrives in the background, and across both bright and dark environments.

## Capabilities and Constraints

- Captures future debit and credit alerts from supported bank SMS and payment apps (Google Pay, PhonePe, Paytm) after permission is granted; it does not import historical messages.
- Stores transaction records, titles, notes, drafts, and settings locally with Room and DataStore.
- Does not initiate payments or request internet, contacts, location, SMS sending, accessibility, overlay, or bank credentials.
- Notification presentation is partly controlled by Android notification channels and system settings.
- The current minimum supported Android version is Android 8.0 (API 26).

## Brand Commitments

The product name is Penny. Violet is the recognizable brand color. The user has explicitly requested a polished glassy finish, first-class light and dark themes, smooth motion, and a distinctive optional Penny notification tone.

## Evidence on Hand

The repository contains working native Android flows for onboarding, SMS permissions, transaction capture, home totals, history filters, expense naming, transfer marking, notifications, and local data deletion. No external claims, testimonials, or cloud-service evidence should be fabricated.

## Product Principles

- Local and understandable by default.
- Capture automatically; ask for only the context automation cannot know.
- Make money movement legible in seconds.
- Treat permissions and destructive actions with clear, honest explanations.
- Preserve Android-native interaction and accessibility behavior.

## Accessibility & Inclusion

Use Material touch targets, semantic labels, scalable text, sufficient contrast in both themes, system Back behavior, and motion that respects Android animation settings.
