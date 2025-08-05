import React, { useRef, useState, useEffect } from 'react';
import { Button, Alert, Spinner } from 'react-bootstrap';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faVideo, faCamera } from '@fortawesome/free-solid-svg-icons';

const CameraCapture = ({ onCapture, buttonText, loading }) => {
    const videoRef = useRef(null);
    const canvasRef = useRef(null);
    const [stream, setStream] = useState(null);
    const [error, setError] = useState('');
    const [isCameraReady, setIsCameraReady] = useState(false);

    useEffect(() => {
        const getCameraStream = async () => {
            try {
                if (!navigator.mediaDevices || !navigator.mediaDevices.getUserMedia) {
                    setError("Your browser does not support camera access.");
                    return;
                }

                const mediaStream = await navigator.mediaDevices.getUserMedia({ video: true });
                setStream(mediaStream);
                if (videoRef.current) {
                    videoRef.current.srcObject = mediaStream;
                    videoRef.current.onloadedmetadata = () => {
                        setIsCameraReady(true);
                    };
                }
                setError('');
            } catch (err) {
                console.error("Error accessing camera: ", err);
                if (err.name === "NotAllowedError") {
                    setError("Camera access denied. Please allow camera permissions in your browser settings.");
                } else if (err.name === "NotFoundError") {
                    setError("No camera found. Please ensure a camera is connected.");
                } else {
                    setError(`Failed to access camera: ${err.message}.`);
                }
                setIsCameraReady(false);
            }
        };

        if (!stream) {
            getCameraStream();
        }

        return () => {
            if (stream) {
                stream.getTracks().forEach(track => track.stop());
                setStream(null);
                setIsCameraReady(false);
            }
        };
    }, []);

    const captureImage = () => {
        if (videoRef.current && canvasRef.current && isCameraReady) {
            const video = videoRef.current;
            const canvas = canvasRef.current;
            const context = canvas.getContext('2d');

            canvas.width = video.videoWidth;
            canvas.height = video.videoHeight;

            context.drawImage(video, 0, 0, canvas.width, canvas.height); // (image, dx, dy, dWidth, dHeight)

            canvas.toBlob((blob) => {
                if (blob) {
                    const fileName = `captured_${Date.now()}.jpeg`;
                    const imageFile = new File([blob], fileName, { type: 'image/jpeg' });

                    onCapture(imageFile);
                } else {
                    setError("Failed to capture image as Blob.");
                }
            }, 'image/jpeg', 0.9); 
        } else if (!isCameraReady) {
            setError("Camera is not ready yet. Please wait or check permissions.");
        }
    };

    return (
        <div className="mb-4 text-center">
            <h5 className="mb-3 text-muted">
                <FontAwesomeIcon icon={faVideo} className="me-2" /> Live Camera Feed
            </h5>
            {error && <Alert variant="danger">{error}</Alert>}
            <div className="position-relative bg-dark rounded overflow-hidden mb-3" style={{ width: '100%', aspectRatio: '16/9' }}>
                {!isCameraReady && !error && (
                    <div className="position-absolute top-50 start-50 translate-middle text-white">
                        <Spinner animation="border" className="me-2" /> Loading Camera...
                    </div>
                )}
                <video
                    ref={videoRef}
                    autoPlay
                    playsInline
                    className={`w-100 h-100 object-fit-cover ${isCameraReady ? '' : 'd-none'}`}
                />
                <canvas ref={canvasRef} style={{ display: 'none' }} /> {/* Canvas is hidden, used for capturing */}
            </div>
            <Button
                onClick={captureImage}
                disabled={loading || !isCameraReady}
                variant="success"
                className="w-100 py-2"
            >
                {loading ? (
                    <>
                        <Spinner animation="border" size="sm" className="me-2" />
                        {buttonText.replace('Capture', 'Processing...')}
                    </>
                ) : (
                    <>
                        <FontAwesomeIcon icon={faCamera} className="me-2" /> {buttonText}
                    </>
                )}
            </Button>
        </div>
    );
};

export default CameraCapture;
