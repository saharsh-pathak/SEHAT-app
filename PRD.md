
# SEHAT — Smart Edge Healthcare Access & Telemedicine Platform

> **Smart India Hackathon 2026 — Problem Statement 26133 (Government of Maharashtra)**

**Version:** 1.0 (Phase 1 Software Architecture)

---

# Project Overview

SEHAT (Smart Edge Healthcare Access & Telemedicine Platform) is an offline-first digital healthcare platform designed for Maharashtra's rural public healthcare system. It is **not a patient application**. The platform is used by ASHA Workers, ANMs, CHOs, PHC doctors, CHC specialists, district hospitals, pharmacists, and lab technicians to manage a patient's complete healthcare journey.

The goal is to strengthen — **not replace** — the existing government healthcare infrastructure by providing digital triage, longitudinal health records, referral tracking, appointment automation, medicine visibility, multilingual voice interaction, and structured follow-up.

---

# SIH Problem Statement Mapping

| SIH Requirement | SEHAT Feature |
|-----------------|---------------|
| Digital Triage | Offline clinical screening engine |
| Appointment & Queue Management | Automatic appointment booking |
| Referral Tracking | Digital referral lifecycle |
| Longitudinal Records | ABHA-linked patient timeline |
| Diagnostics Coordination | Shared reports across facilities |
| Medicine Availability | Government facility stock visibility |
| Emergency Escalation | Automatic emergency routing |
| Multilingual Interaction | Marathi/Hindi/English STT + TTS |
| Low Connectivity | Offline-first Android app |
| Follow-up | Doctor-generated ASHA tasks |
| ABDM/FHIR | ABHA interoperable health records |

---

# Final Project Scope

## Phase 1 (SIH Submission)

- Android application for ASHA / ANM / CHO.
- React web portal for PHC, CHC and hospitals.
- FastAPI backend with PostgreSQL.
- Offline AI screening.
- Offline STT and TTS.
- ABHA integration.
- Referral management.
- Automatic appointment booking.
- Follow-up workflow.

## Phase 2 (Future Roadmap)

- Raspberry Pi 5 healthcare device.
- BP, SpO₂, Temperature and Glucose sensors.
- Bluetooth syncing with SEHAT Android app.
- Automatic vitals capture.

---

# Core Product Principles (Locked Decisions)

1. No patient mobile app.
2. Every patient has one Care Episode linked to ABHA.
3. If ABHA doesn't exist, create it using Aadhaar.
4. Appointment booking is automatic.
5. Teleconsultation exists only in Doctor Portal.
6. Offline sync is automatic using WorkManager.
7. Doctor closes treatment episode.
8. ASHA verifies recovery before final case closure.

---

# Healthcare Infrastructure Workflow

Patient
→ ASHA Home Screening
→ Ayushman Arogya Mandir (AAM-SHC)
→ PHC
→ CHC / FRU
→ District Hospital / Medical College
→ Doctor Discharge
→ ASHA Follow-up
→ Recovery Verification
→ Case Closed

---

# Complete Patient Care Episode

## Stage 1 — Patient Registration

- Search patient using ABHA ID, Name or Mobile.
- If ABHA unavailable:
  - Aadhaar verification.
  - Create ABHA.
  - Attach collected data.

Output:
- Patient Profile.
- Care Episode ID.

## Stage 2 — Symptoms Collection

Healthcare worker records:

- Voice conversation.
- Manual text.
- Medical history.
- Existing diseases.
- Current medicines.
- Allergies.
- Consent.

Speech transcription remains editable.

## Stage 3 — Basic Screening Tests

Mandatory:

- Blood Pressure
- Heart Rate
- SpO₂
- Temperature
- Blood Sugar
- Hemoglobin
- Height
- Weight

Conditional:

- Pregnancy
- Malaria
- Dengue
- Urine
- TB

Clinical Notes field available.

## Stage 4 — Severity Assessment

Inputs:

- Symptoms
- Medical history
- Vitals
- Screening tests

Outputs:

- Probable clinical condition.
- Severity (Mild / Moderate / Severe / Emergency).
- Recommended healthcare facility.
- Recommended healthcare professional.
- Justification.

Healthcare worker must enter clinical notes before continuing.

## Stage 5 — Referral Management

SEHAT generates:

- Referral ID.
- Destination facility.
- Consultation summary.
- Attached reports.

Priority is generated automatically from severity.

## Stage 6 — Automatic Appointment

SEHAT automatically books:

- Destination facility.
- Appointment date.
- Appointment time.
- Queue token.

Patient only needs Name or ABHA ID at the facility.

## Stage 7 — Consultation

Doctor portal displays:

- Timeline.
- Symptoms.
- Vitals.
- Diagnostics.
- Medicines.
- Referral history.

Doctor updates treatment.

## Stage 8 — Diagnostics

Doctor orders diagnostics.

Lab uploads reports.

Reports immediately become part of patient timeline.

## Stage 9 — Medicines

Doctor prescribes medicines.

Android app displays stock availability across nearby government facilities.

No reservation feature.

## Stage 10 — Follow-up

Doctor generates structured follow-up instructions.

ASHA receives task.

Checklist includes:

- Medicine adherence.
- Recovery status.
- Symptoms.
- Vitals.
- Notes.

## Stage 11 — Case Closure

Doctor marks treatment complete.

ASHA performs follow-up.

Positive recovery:
- Case Closed.

If patient worsens:
- Episode Reopened.

---

# Android App (Final UI Flow)

The UI reference images are stored in:

ui-ref/

Screen order:

1. Dashboard
2. Search Patient
3. Symptoms Collection
4. Basic Screening Tests
5. Severity Assessment
6. Patient Timeline
7. Referral Management
8. Appointment Confirmation
9. Medicine Availability
10. Follow-up Tasks
11. Language Selection

---

# Android Screen Specifications

## Screen 1 — Dashboard

Contains:

- Search Patient.
- Today's Visits.
- Pending Follow-ups.
- Notifications.
- Medicine Availability shortcut.
- Today's Schedule.
- Sync Status.

Removed:

- Login.
- Referrals card.
- Appointment card.
- Lab Reports card.
- Medical Alerts.

## Screen 2 — Search Patient

Search by:

- ABHA ID.
- Name.
- Mobile.

Create ABHA if not found.

## Screen 3 — Symptoms Collection

Components:

- Voice recording button.
- Manual text input box.
- Symptom chips.
- Existing conditions.
- Medicines.
- Allergies.
- Consent.

## Screen 4 — Basic Screening Tests

Vitals input.

Conditional tests.

Clinical observations.

## Screen 5 — Severity Assessment

Displays:

- Probable condition.
- Severity.
- Recommended Facility dropdown.
- Recommended Healthcare Professional dropdown.
- Justification.
- Clinical Notes.

No medicine suggestions.

## Screen 6 — Patient Timeline

Longitudinal record showing:

- Visits.
- Diagnostics.
- Prescriptions.
- Referrals.
- Follow-ups.

## Screen 7 — Referral Management

Fields:

- Destination Facility dropdown.
- Referral Notes.
- Attached reports.

Priority generated automatically.

## Screen 8 — Appointment Confirmation

Read-only.

Displays:

- Appointment ID.
- Facility.
- Date.
- Time.
- Queue Token.

No manual booking.

## Screen 9 — Medicine Availability

Shows nearby facility stock.

No reservation.

## Screen 10 — Follow-up Tasks

Checklist generated from doctor instructions.

## Screen 11 — Language Selection

Languages:

- Marathi.
- Hindi.
- English.

Changes STT and TTS language globally.

---

# Speech AI Layer (Offline)

SEHAT supports multilingual voice interaction without internet.

## Speech-to-Text

**Model:** IndicConformer STT (AI4Bharat)

Purpose:

- Marathi speech recognition.
- Hindi speech recognition.
- English speech recognition.

Runtime:

- ONNX Runtime Android.
- 8-bit Quantized ONNX model.

Input pipeline:

Microphone
→ AudioRecord
→ Silero Voice Activity Detection
→ IndicConformer STT
→ Editable Text Field

## Voice Activity Detection

Model:

Silero VAD.

Purpose:

- Detect speech start/end.
- Reduce unnecessary inference.
- Faster transcription.

## Text-to-Speech

**Model:** Kokoro TTS

Purpose:

- Read triage recommendations.
- Read follow-up instructions.
- Read referral guidance.

Runtime:

- ONNX Runtime Android.

Output:

- AudioTrack playback.

## Supported Languages

- Marathi.
- Hindi.
- English.

Speech layer works completely offline after models are downloaded.

---

# AI Screening Engine (Offline)

Components:

TensorFlow Lite
- Symptom classification.

Rule-Based Clinical Engine
- Government screening protocols.

JSON/YAML Disease Protocol Library
- Disease-specific questions.
- Referral rules.
- Emergency red flags.

Risk Classification Engine

Outputs:

- Severity.
- Probable condition.
- Recommended healthcare professional.
- Recommended facility.

Important:

This is **not** a diagnosis engine.

Doctor remains responsible for clinical decisions.

---

# Technology Stack

## Android

- Kotlin
- Android Studio
- Jetpack Compose
- MVVM
- Navigation Compose
- Room Database
- WorkManager
- Encrypted Shared Preferences
- ONNX Runtime Android
- TensorFlow Lite

## Backend

- FastAPI
- PostgreSQL
- SQLAlchemy
- JWT Authentication
- Docker

## Web Portal

- React
- TypeScript
- Tailwind CSS
- shadcn/ui
- React Query

## AI

- IndicConformer STT
- Kokoro TTS
- Silero VAD
- TensorFlow Lite
- Qwen 3.8 27B (Portal only)

---

# Offline First Architecture

Room stores:

- Patients.
- Care Episodes.
- Referrals.
- Diagnostics.
- Medicines.
- Follow-up Tasks.

WorkManager syncs when internet becomes available.

Conflict resolution uses server timestamps.

No offline screen exists.

---

# Database Entities

Patient

- ABHA ID
- Aadhaar Reference
- Name
- DOB
- Gender
- Address
- Mobile

CareEpisode

- Episode ID
- Patient ID
- Facility
- Status
- Created At
- Closed At

Symptoms

Vitals

Diagnostics

Medicines

Referral

Appointment

FollowUpTask

SyncQueue

---

# Doctor & Institute Portal

Modules:

- Doctor Dashboard.
- Consultation.
- Diagnostics.
- Referral Inbox.
- Patient Timeline.
- Medicine Inventory.
- Follow-up Generator.

Teleconsultation exists only here.

---

# Folder Structure

SEHAT/
├── app/
├── backend/
├── ui-ref/
├── docs/
├── assets/
├── project.md
└── README.md

---

# Production Requirements

## Android Minimum

- Android 10+
- ARM64
- 6 GB RAM recommended.
- 2 GB free storage.

## APK Strategy

Do not bundle speech models inside APK.

Download models on first launch.

Models remain offline afterwards.

Estimated APK:

80–120 MB

Estimated model storage:

450–550 MB

---

# Security

- JWT Authentication.
- HTTPS APIs.
- Encrypted Shared Preferences.
- Room encryption for sensitive fields.
- ABHA consent before record access.

---

# Performance Goals

- Patient Search under 1 second.
- Offline patient save under 200 ms.
- STT response within 1–2 seconds after speech ends.
- Automatic sync in background.
- Smooth operation on mid-range Android devices.

---

# Future Phase 2

Hardware integration includes:

- Raspberry Pi 5.
- BP Sensor.
- SpO₂ Sensor.
- Temperature Sensor.
- Glucose Meter.
- Bluetooth syncing into Basic Screening Tests screen.

The software architecture is already designed so Phase 2 hardware plugs into Stage 3 without changing the healthcare workflow.

---

# Development Roadmap (Codex)

Milestone 1
- Theme
- Navigation
- Dashboard

Milestone 2
- Patient Search
- Symptoms Collection
- STT Integration

Milestone 3
- Basic Tests
- Severity Assessment

Milestone 4
- Patient Timeline
- Referral Management

Milestone 5
- Automatic Appointment
- Medicine Availability

Milestone 6
- Follow-up Tasks
- Language Selection
- Offline Sync

Milestone 7
- FastAPI Backend
- PostgreSQL
- Doctor Portal

Milestone 8
- ABDM / ABHA Integration
- FHIR APIs
- Analytics Dashboard

---

# Final Vision

SEHAT digitizes one complete healthcare journey:

Patient → Screening → Severity Assessment → Referral → Consultation → Diagnostics → Medicines → Follow-up → Recovery → Case Closed

The platform strengthens Maharashtra's existing public healthcare infrastructure through offline-first mobile technology, multilingual speech AI, automatic referral coordination, longitudinal health records, and structured follow-up.
