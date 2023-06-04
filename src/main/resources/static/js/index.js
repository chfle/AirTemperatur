/**
 * Redirects the user to a new URL based on the selected range value.
 * @param {string} val - The selected range value.
 */
const getByEnum = (val) => {
   window.location.href = "/?range="+val
}

/**
 * Calculates the average of an array of numbers.
 * @param {Array} data - The array of numbers.
 * @returns {number} The average value.
 */
const calculateAverage = (data) => {
   const sum = data.reduce((acc, value) => acc + value, 0);
   return sum / data.length;
}

/**

 Calculates the threshold based on the given array of numbers.

 @param {number[]} numbers - An array of numbers.

 @returns {number} The calculated threshold value.
 */
function calculateThreshold(numbers) {
// Calculate the average of the numbers
   const average = numbers.reduce((sum, num) => sum + num, 0) / numbers.length;
// Calculate the squared differences from the average
   const squaredDifferences = numbers.map(num => Math.pow(num - average, 2));
// Calculate the variance as the average of squared differences
   const variance = squaredDifferences.reduce((sum, squaredDiff) => sum + squaredDiff, 0) / numbers.length;
// Calculate the standard deviation as the square root of the variance
   const standardDeviation = Math.sqrt(variance);
// Calculate the threshold as the average plus 1.5 times the standard deviation
// Note: 1.5 can be replaced with a different value to adjust the difficulty
   return average + 1.5 * standardDeviation;
}




