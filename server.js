const express = require('express');
const fs = require('fs');
const path = require('path');
const { exec } = require('child_process');

const app = express();
const PORT = 5000;

// Serve static files
app.use(express.static('.'));

// API endpoint to get project structure
app.get('/api/project-structure', (req, res) => {
  exec('find app -type f | grep -v "build/" | sort', (error, stdout, stderr) => {
    if (error) {
      return res.status(500).json({ error: error.message });
    }
    
    const files = stdout.split('\n').filter(line => line.trim() !== '');
    res.json({ files });
  });
});

// API endpoint to get file content
app.get('/api/file-content', (req, res) => {
  const filePath = req.query.path;
  
  if (!filePath) {
    return res.status(400).json({ error: 'No file path provided' });
  }
  
  // Security check - only allow files within the project directory
  const fullPath = path.resolve(filePath);
  const projectRoot = path.resolve('.');
  
  if (!fullPath.startsWith(projectRoot)) {
    return res.status(403).json({ error: 'Access denied' });
  }
  
  fs.readFile(fullPath, 'utf8', (err, data) => {
    if (err) {
      return res.status(500).json({ error: err.message });
    }
    
    // Determine file type for syntax highlighting
    const extension = path.extname(filePath).toLowerCase();
    let fileType = 'text';
    
    if (['.kt', '.java'].includes(extension)) {
      fileType = 'kotlin';
    } else if (['.xml'].includes(extension)) {
      fileType = 'xml';
    } else if (['.gradle', '.kts'].includes(extension)) {
      fileType = 'gradle';
    } else if (['.json'].includes(extension)) {
      fileType = 'json';
    }
    
    res.json({ 
      content: data,
      fileType
    });
  });
});

// API endpoint to check Firebase config
app.get('/api/firebase-status', (req, res) => {
  const firebaseConfigPath = 'app/google-services.json';
  
  fs.readFile(firebaseConfigPath, 'utf8', (err, data) => {
    if (err) {
      return res.json({ 
        configured: false,
        message: 'Firebase configuration file not found'
      });
    }
    
    try {
      const config = JSON.parse(data);
      const apiKey = config.client[0]?.api_key[0]?.current_key;
      
      res.json({
        configured: apiKey && apiKey !== 'YOUR_API_KEY',
        message: apiKey && apiKey !== 'YOUR_API_KEY' 
          ? 'Firebase is properly configured' 
          : 'Firebase API key needs to be set'
      });
    } catch (error) {
      res.json({
        configured: false,
        message: 'Invalid Firebase configuration file'
      });
    }
  });
});

// Start the server
app.listen(PORT, '0.0.0.0', () => {
  console.log(`HabitHive preview server running at http://0.0.0.0:${PORT}`);
});