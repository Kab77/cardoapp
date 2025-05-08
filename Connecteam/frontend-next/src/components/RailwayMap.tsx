import React, { useEffect, useState } from 'react';
import { MapContainer, TileLayer, Polyline, Popup } from 'react-leaflet';
import axios from 'axios';
import { Box, Typography, Paper } from '@mui/material';
import 'leaflet/dist/leaflet.css';

interface Coordinate {
  latitude: number;
  longitude: number;
}

interface RailwayLine {
  id: number;
  name: string;
  startPoint: string;
  endPoint: string;
  coordinates: Coordinate[];
  status: string;
  color: string;
}

const RailwayMap: React.FC = () => {
  const [railwayLines, setRailwayLines] = useState<RailwayLine[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState<string | null>(null);

  useEffect(() => {
    const fetchRailwayLines = async () => {
      try {
        const response = await axios.get('http://localhost:8080/api/railway-lines/map');
        setRailwayLines(response.data);
        setLoading(false);
      } catch (err) {
        setError('Erreur lors du chargement des lignes ferroviaires');
        setLoading(false);
      }
    };

    fetchRailwayLines();
  }, []);

  if (loading) {
    return <Typography>Chargement de la carte...</Typography>;
  }

  if (error) {
    return <Typography color="error">{error}</Typography>;
  }

  return (
    <Box sx={{ height: '80vh', width: '100%', position: 'relative' }}>
      <MapContainer
        center={[46.603354, 1.888334]} // Centre de la France
        zoom={6}
        style={{ height: '100%', width: '100%' }}
      >
        <TileLayer
          url="https://{s}.tile.openstreetmap.org/{z}/{x}/{y}.png"
          attribution='&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
        />
        
        {railwayLines.map((line) => (
          <Polyline
            key={line.id}
            positions={line.coordinates.map(coord => [coord.latitude, coord.longitude])}
            color={line.color}
            weight={3}
          >
            <Popup>
              <Paper sx={{ p: 1 }}>
                <Typography variant="h6">{line.name}</Typography>
                <Typography>Départ: {line.startPoint}</Typography>
                <Typography>Arrivée: {line.endPoint}</Typography>
                <Typography>
                  Statut: {
                    line.status === 'valid' ? 'Valide' :
                    line.status === 'expiring_soon' ? 'Expire bientôt' :
                    'Expiré'
                  }
                </Typography>
              </Paper>
            </Popup>
          </Polyline>
        ))}
      </MapContainer>

      <Paper
        sx={{
          position: 'absolute',
          top: 10,
          right: 10,
          p: 2,
          zIndex: 1000,
          backgroundColor: 'rgba(255, 255, 255, 0.9)'
        }}
      >
        <Typography variant="h6" gutterBottom>Légende</Typography>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
          <Box sx={{ width: 20, height: 3, bgcolor: '#00FF00', mr: 1 }} />
          <Typography>Lignes valides</Typography>
        </Box>
        <Box sx={{ display: 'flex', alignItems: 'center', mb: 1 }}>
          <Box sx={{ width: 20, height: 3, bgcolor: '#FFA500', mr: 1 }} />
          <Typography>Lignes à renouveler</Typography>
        </Box>
        <Box sx={{ display: 'flex', alignItems: 'center' }}>
          <Box sx={{ width: 20, height: 3, bgcolor: '#FF0000', mr: 1 }} />
          <Typography>Lignes expirées</Typography>
        </Box>
      </Paper>
    </Box>
  );
};

export default RailwayMap; 